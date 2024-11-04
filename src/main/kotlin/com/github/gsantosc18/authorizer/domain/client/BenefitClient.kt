package com.github.gsantosc18.authorizer.domain.client

import com.github.gsantosc18.authorizer.domain.vo.Benefit
import com.github.gsantosc18.authorizer.domain.vo.UpdateBenefit

interface BenefitClient {
    fun findByAccountIdAndMcc(accountId: String, mcc: String): Benefit?

    fun updateBalance(request: UpdateBenefit)
}