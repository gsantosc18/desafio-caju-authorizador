package com.github.gsantosc18.authorizer.domain.client

import com.github.gsantosc18.authorizer.domain.vo.Account

interface AccountClient {
    fun findById(accountId: String): Account?
}