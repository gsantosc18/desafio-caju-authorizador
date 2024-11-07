package com.github.gsantosc18.others.benefit.controller

import com.github.gsantosc18.others.benefit.dto.BenefitDTO
import com.github.gsantosc18.others.benefit.dto.UpdateBenefitDTO
import com.github.gsantosc18.others.benefit.service.BenefitService
import java.lang.IllegalStateException
import java.lang.RuntimeException
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/benefit")
class BenefitController(
    private val benefitService: BenefitService
) {
    @GetMapping("/{accountId}")
    fun getByAccount(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<List<BenefitDTO>> =
        benefitService.findByAccountId(accountId)
            .let{ ResponseEntity.ok(it) }

    @PutMapping("/{accountId}")
    fun updateBenefit(
        @PathVariable("accountId") accountId: String,
        @RequestBody updateBenefitDTO: UpdateBenefitDTO
    ): ResponseEntity<Unit> {
        benefitService.update(accountId, updateBenefitDTO)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(IllegalStateException::class, NoSuchElementException::class)
    fun exceptionHandler(ex: RuntimeException): ResponseEntity<Any> =
        ResponseEntity.badRequest()
            .body(mapOf(
                "message" to ex.message
            ))
}