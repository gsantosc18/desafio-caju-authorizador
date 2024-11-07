package com.github.gsantosc18.others.benefit.dto

import com.github.gsantosc18.others.benefit.enums.BenefitType
import com.github.gsantosc18.others.benefit.enums.Operation
import java.math.BigDecimal

data class UpdateBenefitDTO(
    val transactionId: String = "",
    val accountId: String = "",
    val type: BenefitType = BenefitType.FOOD,
    val operation: Operation = Operation.DEBIT,
    var value: BigDecimal = BigDecimal.ZERO
)