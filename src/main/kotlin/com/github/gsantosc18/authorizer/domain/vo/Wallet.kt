package com.github.gsantosc18.authorizer.domain.vo

import java.math.BigDecimal

data class Wallet(
    val accountId: String,
    val balance: BigDecimal
) {
    fun isEnough(value: BigDecimal): Boolean = balance >= value
}