package com.github.gsantosc18.others.benefit.service.impl

import com.github.gsantosc18.others.benefit.dto.BenefitDTO
import com.github.gsantosc18.others.benefit.dto.UpdateBenefitDTO
import com.github.gsantosc18.others.benefit.entity.BenefitEntity
import com.github.gsantosc18.others.benefit.enums.Operation
import com.github.gsantosc18.others.benefit.enums.Operation.DEBIT
import com.github.gsantosc18.others.benefit.repository.BenefitsRepository
import com.github.gsantosc18.others.benefit.service.BenefitService
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDateTime.now
import org.springframework.stereotype.Service

@Service
class BenefitServiceImpl(
    private val benefitsRepository: BenefitsRepository
): BenefitService {
    override fun findByAccountId(accountId: String): List<BenefitDTO> =
        benefitsRepository.findByAccountId(accountId).map(BenefitDTO::from)

    override fun update(accountId: String, updateBenefitDTO: UpdateBenefitDTO) {
        val benefits = benefitsRepository.findByAccountId(accountId)

        if (benefits.isEmpty()) {
            throw IllegalStateException("Account not found")
        }

        val benefit = benefits.firstOrNull{ it.type == updateBenefitDTO.type }
            ?: throw NoSuchElementException("Benefit not found")


        benefit
            .updateValue(updateBenefitDTO.operation, updateBenefitDTO.value)
            .also(benefitsRepository::save)
    }

    private fun BenefitEntity.updateValue(operation: Operation, value: BigDecimal): BenefitEntity {
        val amount = when(operation) {
            DEBIT -> balance - value
            else -> balance + value
        }

        if (amount < ZERO) {
            throw IllegalStateException("Balance cannot be less than zero")
        }

        return copy(
            balance = amount,
            updatedAt = now()
        )
    }
}