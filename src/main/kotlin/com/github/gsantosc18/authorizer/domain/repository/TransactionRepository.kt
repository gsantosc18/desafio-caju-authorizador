package com.github.gsantosc18.authorizer.domain.repository

import com.github.gsantosc18.authorizer.domain.entity.Transaction

interface TransactionRepository {
    fun create(transaction: Transaction)

    fun update(transaction: Transaction)
}