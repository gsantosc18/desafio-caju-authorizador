package com.github.gsantosc18.authorizer.application.usecase.impl

import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentRequest
import com.github.gsantosc18.authorizer.application.usecase.chain.PaymentHandler
import com.github.gsantosc18.authorizer.domain.client.AccountClient
import com.github.gsantosc18.authorizer.domain.client.MerchantClient
import com.github.gsantosc18.authorizer.domain.repository.TransactionRepository
import com.github.gsantosc18.authorizer.domain.vo.Account
import com.github.gsantosc18.authorizer.domain.vo.Merchant
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode.AUTHORIZED
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.math.BigDecimal
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class ProcessPaymentUseCaseImplTest {
    private val transactionRepository: TransactionRepository = mockk()
    private val accountClient: AccountClient = mockk()
    private val merchantClient: MerchantClient = mockk()
    private val paymentHandler: PaymentHandler = mockk()

    private val processPaymentUseCase = ProcessPaymentUseCaseImpl(
        repository = transactionRepository,
        accountClient = accountClient,
        merchantClient = merchantClient,
        paymentHandler = paymentHandler
    )

    @Test
    fun `Must authorize transaction with benefit and return code 00`() {
        val request = ProcessPaymentRequest(
            account = "123",
            totalAmount = BigDecimal(100),
            mcc = "5411",
            merchant = "Padaria seu ZE"
        )

        every { accountClient.findById(request.account) } returns Account(
            id = request.account,
            name = "John Doel",
            active = true
        )

        every { merchantClient.findByName(request.merchant) } returns Merchant(
            merchantId = "1",
            name = request.merchant,
            mcc = request.mcc
        )

        every { paymentHandler.handle(any(), any()) } returns AUTHORIZED

        every { transactionRepository.create(any()) } just runs
        every { transactionRepository.update(any()) } just runs

        val response = processPaymentUseCase.execute(request)

        verify(exactly = 1) { paymentHandler.handle(any(), any()) }
        verify(exactly = 1) { transactionRepository.create(any()) }
        verify(exactly = 2) { transactionRepository.update(any()) }
        assertEquals(AUTHORIZED, response.code)
    }

    @Test
    fun `Must authorize transaction with wallet and return response code 00`() {
        val request = ProcessPaymentRequest(
            account = "123",
            totalAmount = BigDecimal(100),
            mcc = "5411",
            merchant = "Padaria seu ZE"
        )

        every { accountClient.findById(request.account) } returns Account(
            id = request.account,
            name = "John Doel",
            active = true
        )

        every { merchantClient.findByName(request.merchant) } returns Merchant(
            merchantId = "1",
            name = request.merchant,
            mcc = request.mcc
        )

        every { paymentHandler.handle(any(), any()) } returns AUTHORIZED

        every { transactionRepository.create(any()) } just runs
        every { transactionRepository.update(any()) } just runs

        val response = processPaymentUseCase.execute(request)

        verify(exactly = 1) { paymentHandler.handle(any(), any()) }
        verify(exactly = 1) { transactionRepository.create(any()) }
        verify(exactly = 2) { transactionRepository.update(any()) }
        assertEquals(AUTHORIZED, response.code)
    }

    @Test
    fun `Must return internal error if account not exists`() {
        val request = mockk<ProcessPaymentRequest>()

        every { request.account } returns "1"
        every { accountClient.findById(any()) } returns null

        val response = processPaymentUseCase.execute(request)

        assertEquals(ResponseCode.INTERNAL_ERROR, response.code)
    }

    @Test
    fun `Must return internal error if account is not active`() {
        val request = mockk<ProcessPaymentRequest>()
        val account = mockk<Account>()

        every { request.account } returns "1"
        every { account.active } returns false
        every { accountClient.findById(any()) } returns account

        val response = processPaymentUseCase.execute(request)

        assertEquals(ResponseCode.INTERNAL_ERROR, response.code)
    }
}