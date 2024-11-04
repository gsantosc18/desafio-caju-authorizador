package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.client.WalletClient
import com.github.gsantosc18.authorizer.domain.vo.UpdateWallet
import com.github.gsantosc18.authorizer.domain.vo.Wallet
import org.springframework.stereotype.Component

@Component
class WalletClientImpl(
    private val walletHttp: WalletHttp
): WalletClient {
    override fun findByAccount(accountId: String): Wallet? {
        return walletHttp.findByAccount(accountId)
    }

    override fun updateBalance(request: UpdateWallet) {
        val response = walletHttp.update(request.accountId, request)
        if (!response.statusCode.is2xxSuccessful) {
            throw IllegalStateException("Wallet balance change was not successful")
        }
    }
}