package com.github.gsantosc18.authorizer.domain.vo

import java.math.BigDecimal
import java.util.UUID

data class UpdateWallet(
    val transactionId: UUID,
    val accountId: String,
    val operation: Operation,
    val value: BigDecimal,
)