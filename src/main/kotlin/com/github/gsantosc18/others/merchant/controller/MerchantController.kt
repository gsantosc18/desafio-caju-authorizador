package com.github.gsantosc18.others.merchant.controller

import com.github.gsantosc18.others.merchant.entity.MerchantEntity
import com.github.gsantosc18.others.merchant.repository.MerchantRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/merchant")
class MerchantController(
    private val repository: MerchantRepository
) {

    @GetMapping("/{name}")
    fun findMerchant(
        @PathVariable("name") name: String
    ): ResponseEntity<MerchantEntity> =
        repository.findByName(name)
            .let { ResponseEntity.ok(it) }

}