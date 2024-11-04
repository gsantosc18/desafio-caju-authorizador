package com.github.gsantosc18.authorizer.domain.client

import com.github.gsantosc18.authorizer.domain.vo.Merchant

interface MerchantClient {
    fun findByName(name: String): Merchant?
}