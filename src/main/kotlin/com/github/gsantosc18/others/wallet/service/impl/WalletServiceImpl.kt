package com.github.gsantosc18.others.wallet.service.impl

import com.github.gsantosc18.others.wallet.dto.UpdateWalletDTO
import com.github.gsantosc18.others.wallet.dto.WalletDTO
import com.github.gsantosc18.others.wallet.entity.WalletEntity
import com.github.gsantosc18.others.wallet.enums.Operation
import com.github.gsantosc18.others.wallet.repository.WalletRepository
import com.github.gsantosc18.others.wallet.service.WalletService
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository
): WalletService {
    override fun findByAccountId(accountId: String): WalletDTO? =
        walletRepository.findByAccountId(accountId)
            ?.let(WalletDTO::from)


    override fun update(accountId: String, updateWalletDTO: UpdateWalletDTO) {
        val walletEntity = walletRepository.findByAccountId(accountId) ?: throw IllegalStateException("Wallet not found")
        walletEntity
            .update(updateWalletDTO.operation, updateWalletDTO.value)
            .let(walletRepository::save)
    }

    private fun WalletEntity.update(operation: Operation, value: BigDecimal): WalletEntity {
        val amount = when(operation) {
            Operation.DEBIT -> balance - value
            else -> balance + value
        }

        if (amount < ZERO) {
            throw IllegalStateException("Balance cannot be less than zero")
        }

        return copy(
            balance = amount,
            updatedAt = LocalDateTime.now()
        )
    }
}