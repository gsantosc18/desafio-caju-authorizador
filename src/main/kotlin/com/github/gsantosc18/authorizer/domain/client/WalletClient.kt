package com.github.gsantosc18.authorizer.domain.client

import com.github.gsantosc18.authorizer.domain.vo.UpdateWallet
import com.github.gsantosc18.authorizer.domain.vo.Wallet

interface WalletClient {
    fun findByAccount(accountId: String): Wallet?

    fun updateBalance(request: UpdateWallet)
}