package com.github.gsantosc18.authorizer.infrastruture.database.persistence

import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.repository.TransactionRepository
import com.github.gsantosc18.authorizer.infrastruture.database.mapper.Mapper
import com.github.gsantosc18.authorizer.infrastruture.database.repository.TransactionJdbcRepository
import org.springframework.stereotype.Component

@Component
class TransactionPersistence(
    private val repository: TransactionJdbcRepository
): TransactionRepository {
    override fun create(transaction: Transaction) {
        Mapper.from(transaction)
            .also(repository::save)
    }

    override fun update(transaction: Transaction) {
        Mapper.from(transaction)
            .also(repository::save)
    }
}