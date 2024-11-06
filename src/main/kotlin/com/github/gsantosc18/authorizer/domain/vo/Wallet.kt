package com.github.gsantosc18.authorizer.domain.vo

import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Wallet(
    val accountId: String,
    val balance: BigDecimal
) {
    fun isEnough(value: BigDecimal): Boolean = balance >= value && (balance - value) > ZERO
}