package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.client.AccountClient
import com.github.gsantosc18.authorizer.domain.vo.Account
import org.springframework.stereotype.Component

@Component
class AccountClientImpl(
    private val accountHttp: AccountHttp
): AccountClient {
    override fun findById(accountId: String): Account? {
        return accountHttp.findById(accountId)
    }
}