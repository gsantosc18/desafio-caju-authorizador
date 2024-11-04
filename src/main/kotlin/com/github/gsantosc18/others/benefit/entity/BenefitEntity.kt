package com.github.gsantosc18.others.benefit.entity

import com.github.gsantosc18.others.benefit.enums.BenefitType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "benefit")
data class BenefitEntity(
    @Id
    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    val id: UUID = UUID.randomUUID(),
    val accountId: String = "",
    @Enumerated(STRING)
    val type: BenefitType = BenefitType.FOOD,
    val balance: BigDecimal = BigDecimal.ZERO,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)