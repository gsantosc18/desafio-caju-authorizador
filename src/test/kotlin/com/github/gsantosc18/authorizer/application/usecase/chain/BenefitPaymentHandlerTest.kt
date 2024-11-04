package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.client.BenefitClient
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.Benefit
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.others.benefit.enums.BenefitType
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class BenefitPaymentHandlerTest {
    private val benefitClient: BenefitClient = mockk()

    private val benefitPaymentHandler = BenefitPaymentHandler(
        benefitClient = benefitClient
    )

    @Test
    fun `Must process benefit payment and authorize`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )

        val benefit: Benefit = mockk()
        val transaction = mockk<Transaction>()

        every { benefit.isEnough(any()) } returns true
        every { benefit.type } returns BenefitType.FOOD
        every { transaction.id } returns UUID.randomUUID()
        every { benefitClient.findByAccountIdAndMcc("1", "5411") } returns benefit
        every { benefitClient.updateBalance(any()) } just runs

        val responseCode = benefitPaymentHandler.handle(request, transaction)

        assertEquals(ResponseCode.AUTHORIZED, responseCode)
    }

    @Test
    fun `Must throw exception and return internal error code`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )

        val benefit: Benefit = mockk()
        val transaction = mockk<Transaction>()

        every { benefit.isEnough(any()) } returns true
        every { benefit.type } returns BenefitType.FOOD
        every { transaction.id } returns UUID.randomUUID()
        every { benefitClient.findByAccountIdAndMcc("1", "5411") } returns benefit
        every { benefitClient.updateBalance(any()) } throws Exception("Internal error")

        val responseCode = benefitPaymentHandler.handle(request, transaction)

        assertEquals(ResponseCode.INTERNAL_ERROR, responseCode)
    }

    @Test
    fun `Mustn't found benefit and must to go to next handle`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )
        val transaction = mockk<Transaction>()

        every { benefitClient.findByAccountIdAndMcc("1", "5411") } returns null

        benefitPaymentHandler.handle(request, transaction)

        verify(exactly = 0) { benefitClient.updateBalance(any()) }
    }
}