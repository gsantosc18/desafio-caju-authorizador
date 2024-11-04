package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.vo.UpdateWallet
import com.github.gsantosc18.authorizer.domain.vo.Wallet
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "wallet", url = "\${client.wallet}")
interface WalletHttp {
    @GetMapping("/wallet/{accountId}")
    fun findByAccount(@PathVariable("accountId") accountId: String): Wallet

    @PutMapping("/wallet/{accountId}")
    fun update(@PathVariable accountId: String, request: UpdateWallet): ResponseEntity<Any>
}