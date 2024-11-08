package com.github.gsantosc18.others.benefit.dto

import com.github.gsantosc18.others.benefit.entity.BenefitEntity
import com.github.gsantosc18.others.benefit.enums.BenefitType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class BenefitDTO (
    val id: UUID,
    val accountId: String,
    val type: BenefitType,
    val balance: BigDecimal,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(entity: BenefitEntity): BenefitDTO =
            BenefitDTO(
                id = entity.id,
                accountId = entity.accountId,
                type = entity.type,
                balance = entity.balance,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
    }
}