package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.client.MerchantClient
import com.github.gsantosc18.authorizer.domain.vo.Merchant
import org.springframework.stereotype.Component

@Component
class MerchantClientImpl(
    private val merchantHttp: MerchantHttp
): MerchantClient {
    override fun findByName(name: String): Merchant? {
        return merchantHttp.findByName(name)
    }
}