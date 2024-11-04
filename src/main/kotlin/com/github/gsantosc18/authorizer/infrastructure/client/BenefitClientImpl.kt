package com.github.gsantosc18.authorizer.infrastructure.client

import com.github.gsantosc18.authorizer.domain.client.BenefitClient
import com.github.gsantosc18.authorizer.domain.vo.Benefit
import com.github.gsantosc18.authorizer.domain.vo.UpdateBenefit
import org.springframework.stereotype.Component

@Component
class BenefitClientImpl(
    private val benefitHttp: BenefitHttp
): BenefitClient {
    override fun findByAccountIdAndMcc(accountId: String, mcc: String): Benefit? {
        val benefits: List<Benefit> = benefitHttp.findByAccountId(accountId)

        return benefits.firstOrNull { it.type.mcc.contains(mcc) }
    }

    override fun updateBalance(request: UpdateBenefit) {
        val response = benefitHttp.updateBalance(request.accountId, request)
        if (!response.statusCode.is2xxSuccessful) {
            throw IllegalStateException("Benefit balance changed was not successful")
        }
    }
}