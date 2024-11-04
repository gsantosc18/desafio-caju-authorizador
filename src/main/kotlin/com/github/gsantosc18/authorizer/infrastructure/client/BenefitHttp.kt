package com.github.gsantosc18.authorizer.infrastructure.client

import com.github.gsantosc18.authorizer.domain.vo.Benefit
import com.github.gsantosc18.authorizer.domain.vo.UpdateBenefit
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "benefit", url = "\${client.benefit}")
interface BenefitHttp {

    @GetMapping("/benefit/{accountId}")
    fun findByAccountId(@PathVariable("accountId") accountId: String): List<Benefit>
    @PutMapping("/benefit/{accountId}")
    fun updateBalance(@PathVariable("accountId") accountId: String, request: UpdateBenefit): ResponseEntity<Any>
}