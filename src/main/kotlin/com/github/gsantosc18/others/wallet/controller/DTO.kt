package com.github.gsantosc18.others.wallet.controller

import com.github.gsantosc18.authorizer.domain.vo.Operation
import java.math.BigDecimal

data class UpdateWallet(
    val transactionId: String = "",
    val accountId: String = "",
    val operation: Operation = Operation.DEBIT,
    val value: BigDecimal = BigDecimal.ZERO,
)