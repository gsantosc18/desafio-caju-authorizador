package com.github.gsantosc18.others.merchant.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Types
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "merchants")
data class MerchantEntity(
    @Id @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    val id: UUID,
    val name: String,
    val mcc: String
)
