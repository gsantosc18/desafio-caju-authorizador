package com.github.gsantosc18.others.wallet.dto

import com.github.gsantosc18.others.wallet.enums.Operation
import java.math.BigDecimal

data class UpdateWalletDTO(
    val transactionId: String,
    val accountId: String,
    val operation: Operation,
    val value: BigDecimal,
)