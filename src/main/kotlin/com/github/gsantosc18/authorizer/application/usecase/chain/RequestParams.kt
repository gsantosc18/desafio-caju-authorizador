package com.github.gsantosc18.authorizer.application.usecase.chain

import java.math.BigDecimal

data class RequestParams(
    val accountId: String,
    val mcc: String,
    val value: BigDecimal
)