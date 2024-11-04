package com.github.gsantosc18.authorizer.application.usecase.mapper

import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentRequest
import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus.STARTED
import java.time.LocalDateTime.now
import java.util.UUID

object Mapper {
    fun from(request: ProcessPaymentRequest, mcc: String): Transaction =
        with(request) {
            Transaction(
                id = UUID.randomUUID(),
                accountId = account,
                merchantName = merchant,
                totalAmount = totalAmount,
                mcc = mcc,
                status = STARTED,
                createdAt = now(),
                updatedAt = now()
            )
        }
}