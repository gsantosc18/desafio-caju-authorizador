package com.github.gsantosc18.others.wallet.dto

import com.github.gsantosc18.others.wallet.entity.WalletEntity
import java.math.BigDecimal

data class WalletDTO(
    val accountId: String,
    val balance: BigDecimal
) {

    companion object {
        fun from(entity: WalletEntity): WalletDTO =
            WalletDTO(
                accountId = entity.accountId,
                balance = entity.balance
            )
    }

}