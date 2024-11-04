package com.github.gsantosc18.others.benefit.controller

import com.github.gsantosc18.authorizer.domain.vo.Operation
import com.github.gsantosc18.others.benefit.entity.BenefitEntity
import com.github.gsantosc18.others.benefit.repository.BenefitsRepository
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/benefit")
class BenefitController(
    private val repository: BenefitsRepository
) {
    @GetMapping("/{accountId}")
    fun getByAccount(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<List<BenefitEntity>> =
        repository.findByAccountId(accountId)
            .let{ ResponseEntity.ok(it) }

    @PutMapping("/{accountId}")
    fun updateBenefit(
        @PathVariable("accountId") accountId: UUID,
        @RequestBody updateBenefit: UpdateBenefit
    ): ResponseEntity<Unit> {
        val benefitEntities = repository.findByAccountId(requireNotNull(updateBenefit.accountId))
        val benefit = benefitEntities.first { it.type == updateBenefit.type }
        val value = when(updateBenefit.operation) {
            Operation.DEBIT -> benefit.balance - requireNotNull(updateBenefit.value)
            else -> benefit.balance + requireNotNull(updateBenefit.value)
        }
        benefit.copy(
            balance = value,
            updatedAt = LocalDateTime.now()
        ).let(repository::save)
        return ResponseEntity.ok().build()
    }
}