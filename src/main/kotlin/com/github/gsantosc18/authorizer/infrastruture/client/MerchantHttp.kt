package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.vo.Merchant
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "merchant", url = "\${client.merchant}")
interface MerchantHttp {
    @GetMapping("/merchant/{name}")
    fun findByName(@PathVariable("name") name: String): Merchant?
}