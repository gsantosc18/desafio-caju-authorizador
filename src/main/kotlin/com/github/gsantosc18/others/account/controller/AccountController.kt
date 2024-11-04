package com.github.gsantosc18.others.account.controller

import com.github.gsantosc18.others.account.entity.AccountEntity
import com.github.gsantosc18.others.account.repository.AccountRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val repository: AccountRepository
) {

    @GetMapping("/{accountId}")
    fun find(
        @PathVariable("accountId") accountId: UUID
    ): ResponseEntity<AccountEntity> =
        repository.findByIdOrNull(accountId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}