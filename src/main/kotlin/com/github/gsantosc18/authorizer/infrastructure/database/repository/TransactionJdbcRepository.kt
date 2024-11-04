package com.github.gsantosc18.authorizer.infrastructure.database.repository

import com.github.gsantosc18.authorizer.infrastructure.database.entity.TransactionEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJdbcRepository: JpaRepository<TransactionEntity, UUID>