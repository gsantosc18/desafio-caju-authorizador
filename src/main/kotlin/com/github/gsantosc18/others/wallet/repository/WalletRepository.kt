package com.github.gsantosc18.others.wallet.repository

import com.github.gsantosc18.others.wallet.entity.WalletEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository: JpaRepository<WalletEntity, UUID> {
    fun findByAccountId(accountId: String): WalletEntity?
}