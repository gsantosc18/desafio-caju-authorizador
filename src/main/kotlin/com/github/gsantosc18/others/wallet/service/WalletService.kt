package com.github.gsantosc18.others.wallet.service

import com.github.gsantosc18.others.wallet.dto.UpdateWalletDTO
import com.github.gsantosc18.others.wallet.dto.WalletDTO


interface WalletService {
    fun findByAccountId(accountId: String): WalletDTO?

    fun update(accountId: String, updateWalletDTO: UpdateWalletDTO)
}