package com.github.gsantosc18.others.account.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Types
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    val id: UUID? = null,
    val name: String? = null,
    val active: Boolean? = null
)
