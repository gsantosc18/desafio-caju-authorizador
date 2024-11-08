package com.github.gsantosc18.others.account.controller

import com.github.gsantosc18.others.account.dto.AccountDTO
import com.github.gsantosc18.others.account.service.AccountService
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/{accountId}")
    fun find(
        @PathVariable("accountId") accountId: UUID
    ): ResponseEntity<AccountDTO> =
        accountService.findById(accountId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}