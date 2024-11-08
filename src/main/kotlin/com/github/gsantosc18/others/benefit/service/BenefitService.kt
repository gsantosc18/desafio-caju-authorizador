package com.github.gsantosc18.others.benefit.service

import com.github.gsantosc18.others.benefit.dto.BenefitDTO
import com.github.gsantosc18.others.benefit.dto.UpdateBenefitDTO

interface BenefitService {
    fun findByAccountId(accountId: String): List<BenefitDTO>

    fun update(accountId: String, updateBenefitDTO: UpdateBenefitDTO)
}