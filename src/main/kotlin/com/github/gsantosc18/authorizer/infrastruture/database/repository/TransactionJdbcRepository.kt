package com.github.gsantosc18.authorizer.infrastruture.database.repository

import com.github.gsantosc18.authorizer.infrastruture.database.entity.TransactionEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJdbcRepository: JpaRepository<TransactionEntity, UUID>