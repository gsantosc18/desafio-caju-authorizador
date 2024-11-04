package com.github.gsantosc18.authorizer.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WalletTest {

    @Test
    fun `isEnough should return true when balance is greater than value`() {
        val wallet = Wallet(accountId = "12345", balance = BigDecimal("100.00"))
        val result = wallet.isEnough(BigDecimal("50.00"))

        assertThat(result).isTrue
    }

    @Test
    fun `isEnough should return true when balance is equal to value`() {
        val wallet = Wallet(accountId = "12345", balance = BigDecimal("100.00"))
        val result = wallet.isEnough(BigDecimal("100.00"))

        assertThat(result).isTrue
    }

    @Test
    fun `isEnough should return false when balance is less than value`() {
        val wallet = Wallet(accountId = "12345", balance = BigDecimal("50.00"))
        val result = wallet.isEnough(BigDecimal("100.00"))

        assertThat(result).isFalse
    }
}