package com.github.gsantosc18.others.wallet.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "wallet")
data class WalletEntity(
    @Id @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    val id: UUID = UUID.randomUUID(),
    val accountId: String = "",
    val balance: BigDecimal = BigDecimal.ZERO,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
