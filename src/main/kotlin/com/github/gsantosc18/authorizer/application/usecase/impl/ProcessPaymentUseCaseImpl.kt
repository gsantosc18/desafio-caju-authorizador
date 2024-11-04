package com.github.gsantosc18.authorizer.application.usecase.impl

import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentRequest
import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentResponse
import com.github.gsantosc18.authorizer.application.usecase.ProcessPaymentUseCase
import com.github.gsantosc18.authorizer.application.usecase.chain.PaymentHandler
import com.github.gsantosc18.authorizer.application.usecase.chain.RequestParams
import com.github.gsantosc18.authorizer.application.usecase.mapper.Mapper
import com.github.gsantosc18.authorizer.domain.client.AccountClient
import com.github.gsantosc18.authorizer.domain.client.MerchantClient
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.repository.TransactionRepository
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus.COMPLETED
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus.FAILED
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus.PROCESSING
import org.slf4j.LoggerFactory

class ProcessPaymentUseCaseImpl(
    private val repository: TransactionRepository,
    private val accountClient: AccountClient,
    private val merchantClient: MerchantClient,
    private val paymentHandler: PaymentHandler
): ProcessPaymentUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun execute(request: ProcessPaymentRequest): ProcessPaymentResponse {
        logger.info("Process payment started")
        logger.debug("Process payment started, request={}", request)
        val account = accountClient.findById(request.account)
            .also { logger.debug("Account client response={}", it) }
            ?: return ProcessPaymentResponse(ResponseCode.INTERNAL_ERROR)

        if (!account.active) {
            logger.info("Account is not active")
            logger.debug("Account is not active, account={}", account)
            return ProcessPaymentResponse(ResponseCode.INTERNAL_ERROR)
        }

        val merchant = merchantClient.findByName(request.merchant)
            .also { logger.debug("Mechant response={}", it) }
        val transaction = Mapper.from(request, merchant?.mcc ?: request.mcc)

        repository.create(transaction)
        logger.info("Created new transaction")
        logger.debug("Created new transaction={}", transaction)

        return try {
            transaction.update(PROCESSING)
            logger.info("Update transaction to processing")
            logger.debug("Update transaction to processing={}", transaction)
            val requestParams = RequestParams(
                accountId = request.account,
                mcc = merchant?.mcc ?: request.mcc,
                value = request.totalAmount
            )
            val paymentResponse = paymentHandler.handle(requestParams, transaction)
            logger.debug("Response payment handler, request={}, response={}", requestParams, paymentResponse)

            transaction.apply{
                updateResponseCode(paymentResponse)
                update(COMPLETED)
            }
            logger.info("Updated transaction status to completed")
            logger.info("Updated transaction status to completed={}", transaction)

            ProcessPaymentResponse(paymentResponse)
        } catch (ex: Exception) {
            transaction.update(FAILED)
            logger.info("Failed transaction and updated status")
            logger.info("Failed transaction and updated status={}", transaction)
            ProcessPaymentResponse(ResponseCode.INTERNAL_ERROR)
        }
    }

    private fun Transaction.update(status: TransactionStatus) {
        updateStatus(status)
        repository.update(this)
    }
}