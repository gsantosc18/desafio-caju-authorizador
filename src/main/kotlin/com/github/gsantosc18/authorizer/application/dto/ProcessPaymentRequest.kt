package com.github.gsantosc18.authorizer.application.dto

import java.math.BigDecimal

data class ProcessPaymentRequest(
    val account: String,
    val totalAmount: BigDecimal,
    val mcc: String,
    val merchant: String
)