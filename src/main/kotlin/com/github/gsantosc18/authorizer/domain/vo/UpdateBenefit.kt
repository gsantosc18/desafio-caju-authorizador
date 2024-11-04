package com.github.gsantosc18.authorizer.domain.vo

import com.github.gsantosc18.others.benefit.enums.BenefitType
import java.math.BigDecimal
import java.util.UUID

data class UpdateBenefit(
    val transactionId: UUID,
    val accountId: String,
    val type: BenefitType,
    val operation: Operation,
    val value: BigDecimal
)