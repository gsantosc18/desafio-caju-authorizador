package com.github.gsantosc18.authorizer.infrastructure.database.entity

import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.TransactionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode

@Entity
@Table(name = "transaction")
class TransactionEntity(
    @Id @JdbcTypeCode(Types.VARCHAR)
    val id: UUID = UUID.randomUUID(),
    @Column(name = "account_id")
    val accountId: String = "",
    @Column(name = "total_amount")
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    val mcc: String = "",
    @Column(name = "merchant_name")
    val merchantName: String = "",
    @Column(name = "response_code", nullable = true)
    @Enumerated(EnumType.STRING)
    var responseCode: ResponseCode? = null,
    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.STARTED,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)