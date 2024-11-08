package com.github.gsantosc18.others.wallet.controller

import com.github.gsantosc18.others.wallet.dto.UpdateWalletDTO
import com.github.gsantosc18.others.wallet.dto.WalletDTO
import com.github.gsantosc18.others.wallet.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallet")
class WalletController(
    private val walletService: WalletService
) {

    @GetMapping("/{accountId}")
    fun getByAccountId(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<WalletDTO> =
        walletService.findByAccountId(accountId)
            .let{ ResponseEntity.ok(it) }

    @PutMapping("/{accountId}")
    fun updateWallet(
        @PathVariable("accountId") accountId: String,
        @RequestBody updateWalletDTO: UpdateWalletDTO
    ): ResponseEntity<Unit> {
        walletService.update(accountId, updateWalletDTO)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(IllegalStateException::class)
    fun exceptionHandler(ex: RuntimeException): ResponseEntity<Any> =
        ResponseEntity.badRequest()
            .body(mapOf(
                "message" to ex.message
            ))
}