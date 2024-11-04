package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.client.BenefitClient
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.Operation
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.UpdateBenefit

class BenefitPaymentHandler(
    private val benefitClient: BenefitClient
): AbstractPaymentHandler() {

    override fun handle(request: RequestParams, transaction: Transaction): ResponseCode {
        val benefit = benefitClient.findByAccountIdAndMcc(
            accountId = request.accountId,
            mcc = request.mcc
        )
        return if (benefit != null && benefit.isEnough(request.value)) {
            val updateRequest = UpdateBenefit(
                transactionId = transaction.id,
                accountId = request.accountId,
                type = benefit.type,
                operation = Operation.DEBIT,
                value = request.value
            )
            try {
                benefitClient.updateBalance(updateRequest)
                ResponseCode.AUTHORIZED
            } catch (ex: Exception) {
                ResponseCode.INTERNAL_ERROR
            }
        } else super.handle(request, transaction)
    }

}