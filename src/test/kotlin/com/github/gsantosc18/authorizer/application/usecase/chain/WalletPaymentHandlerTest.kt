package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.client.WalletClient
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.Wallet
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class WalletPaymentHandlerTest {
    private val walletClient: WalletClient = mockk()
    private val walletPaymentHandler = WalletPaymentHandler(walletClient = walletClient)

    @Test
    fun `Must process wallet and return code 00`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )
        val transaction = mockk<Transaction>()
        val wallet = mockk<Wallet>()

        every { transaction.id } returns UUID.randomUUID()
        every { walletClient.findByAccount(any()) } returns wallet
        every { wallet.isEnough(any()) } returns true
        every { walletClient.updateBalance(any()) } just runs

        val responseCode = walletPaymentHandler.handle(request, transaction)

        assertEquals(ResponseCode.AUTHORIZED, responseCode)
    }

    @Test
    fun `Must throw exception and return internal error code`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )
        val transaction = mockk<Transaction>()
        val wallet = mockk<Wallet>()

        every { transaction.id } returns UUID.randomUUID()
        every { walletClient.findByAccount(any()) } returns wallet
        every { wallet.isEnough(any()) } returns true
        every { walletClient.updateBalance(any()) } throws Exception("Internal error")

        val responseCode = walletPaymentHandler.handle(request, transaction)

        assertEquals(ResponseCode.INTERNAL_ERROR, responseCode)
    }

    @Test
    fun `Mustn't found wallet and must to go to next`() {
        val request = RequestParams(
            accountId = "1",
            mcc = "5411",
            value = BigDecimal(100)
        )
        val transaction = mockk<Transaction>()

        every { transaction.id } returns UUID.randomUUID()
        every { walletClient.findByAccount(any()) } returns null

        walletPaymentHandler.handle(request, transaction)

        verify(exactly = 0) { walletClient.updateBalance(any()) }
    }
}