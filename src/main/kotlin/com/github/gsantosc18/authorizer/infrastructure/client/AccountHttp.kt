package com.github.gsantosc18.authorizer.infrastructure.client

import com.github.gsantosc18.authorizer.domain.vo.Account
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "account", url = "\${client.account}")
interface AccountHttp {

    @GetMapping("/account/{accountId}")
    fun findById(@PathVariable("accountId") accountId: String): Account?

}