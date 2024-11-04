package com.github.gsantosc18.authorizer.infrastructure.database.mapper

import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.infrastructure.database.entity.TransactionEntity

object Mapper {
    fun from(transaction: Transaction): TransactionEntity =
        with(transaction) {
            TransactionEntity(
                id = id,
                accountId = accountId,
                totalAmount = totalAmount,
                mcc = mcc,
                merchantName = merchantName,
                responseCode = responseCode,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
}