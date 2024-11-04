package com.github.gsantosc18.others.benefit.repository

import com.github.gsantosc18.others.benefit.entity.BenefitEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface BenefitsRepository: JpaRepository<BenefitEntity, UUID> {
    fun findByAccountId(accountId: String): List<BenefitEntity>
}