package com.github.gsantosc18.authorizer.domain.entity

import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    val id: UUID,
    val accountId: String,
    val totalAmount: BigDecimal,
    val mcc: String,
    val merchantName: String,
    var responseCode: ResponseCode? = null,
    var status: TransactionStatus,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
    fun updateResponseCode(responseCode: ResponseCode) {
        this.responseCode = responseCode
    }

    fun updateStatus(status: TransactionStatus) {
        if (this.status > status) {
            throw IllegalArgumentException("Status must not be less than previous")
        }
        this.status = status
    }
}
