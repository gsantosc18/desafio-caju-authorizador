package com.github.gsantosc18.others.benefit.controller

import com.github.gsantosc18.authorizer.domain.vo.Operation
import com.github.gsantosc18.others.benefit.enums.BenefitType
import java.math.BigDecimal

data class UpdateBenefit(
    val transactionId: String = "",
    val accountId: String = "",
    val type: BenefitType = BenefitType.FOOD,
    val operation: Operation = Operation.DEBIT,
    var value: BigDecimal = BigDecimal.ZERO
)