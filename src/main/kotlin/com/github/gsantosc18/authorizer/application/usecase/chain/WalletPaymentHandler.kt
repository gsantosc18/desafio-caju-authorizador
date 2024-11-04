package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.client.WalletClient
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.Operation
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.UpdateWallet

class WalletPaymentHandler(
    private val walletClient: WalletClient
): AbstractPaymentHandler() {
    override fun handle(request: RequestParams, transaction: Transaction): ResponseCode {
        val wallet = walletClient.findByAccount(request.accountId)
        return if (wallet != null && wallet.isEnough(request.value)) {
            val updateRequest = UpdateWallet(
                transactionId = transaction.id,
                accountId = request.accountId,
                operation = Operation.DEBIT,
                value = request.value
            )
            try {
                walletClient.updateBalance(updateRequest)
                ResponseCode.AUTHORIZED
            } catch (ex: Exception) {
                ResponseCode.INTERNAL_ERROR
            }
        } else super.handle(request, transaction)
    }
}