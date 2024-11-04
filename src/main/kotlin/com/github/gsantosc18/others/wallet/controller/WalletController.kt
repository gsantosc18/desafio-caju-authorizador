package com.github.gsantosc18.others.wallet.controller

import com.github.gsantosc18.authorizer.domain.vo.Operation
import com.github.gsantosc18.others.wallet.entity.WalletEntity
import com.github.gsantosc18.others.wallet.repository.WalletRepository
import java.time.LocalDateTime
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallet")
class WalletController(
    private val repository: WalletRepository
) {

    @GetMapping("/{accountId}")
    fun getByAccountId(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<WalletEntity?> =
        repository.findByAccountId(accountId)
            .let{ ResponseEntity.ok(it) }

    @PutMapping("/{accountId}")
    fun updateWallet(
        @PathVariable("accountId") accountId: String,
        @RequestBody updateWallet: UpdateWallet
    ): ResponseEntity<Unit> {
        val wallet: WalletEntity = repository.findByAccountId(updateWallet.accountId) ?: throw IllegalStateException("Wallet not found")
        val value = when(updateWallet.operation) {
            Operation.DEBIT -> wallet.balance - requireNotNull(updateWallet.value)
            else -> wallet.balance + requireNotNull(updateWallet.value)
        }
        wallet.copy(
            balance = value,
            updatedAt = LocalDateTime.now()
        ).let(repository::save)
        return ResponseEntity.ok().build()
    }
}