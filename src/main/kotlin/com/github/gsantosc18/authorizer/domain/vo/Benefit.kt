package com.github.gsantosc18.authorizer.domain.vo

import com.github.gsantosc18.others.benefit.enums.BenefitType
import java.math.BigDecimal

data class Benefit(
    val accountId: String,
    val balance: BigDecimal,
    val type: BenefitType
) {
    fun isEnough(value: BigDecimal): Boolean = balance >= value
}