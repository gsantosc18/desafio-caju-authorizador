package com.github.gsantosc18.authorizer.domain.vo

import com.github.gsantosc18.others.benefit.enums.BenefitType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BenefitTest {

    @Test
    fun `isEnough should return true when balance is greater than value`() {
        val benefit = Benefit(accountId = "12345", balance = BigDecimal("200.00"), type = BenefitType.FOOD)
        val result = benefit.isEnough(BigDecimal("100.00"))

        assertThat(result).isTrue
    }

    @Test
    fun `isEnough should return true when balance is equal to value`() {
        val benefit = Benefit(accountId = "12345", balance = BigDecimal("100.00"), type = BenefitType.FOOD)
        val result = benefit.isEnough(BigDecimal("100.00"))

        assertThat(result).isTrue
    }

    @Test
    fun `isEnough should return false when balance is less than value`() {
        val benefit = Benefit(accountId = "12345", balance = BigDecimal("50.00"), type = BenefitType.FOOD)
        val result = benefit.isEnough(BigDecimal("100.00"))

        assertThat(result).isFalse
    }
}