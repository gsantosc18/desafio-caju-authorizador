package com.github.gsantosc18.others.benefit.service.impl

import com.github.gsantosc18.others.benefit.dto.BenefitDTO
import com.github.gsantosc18.others.benefit.dto.UpdateBenefitDTO
import com.github.gsantosc18.others.benefit.entity.BenefitEntity
import com.github.gsantosc18.others.benefit.enums.BenefitType.FOOD
import com.github.gsantosc18.others.benefit.enums.BenefitType.MEAL
import com.github.gsantosc18.others.benefit.enums.Operation
import com.github.gsantosc18.others.benefit.enums.Operation.DEBIT
import com.github.gsantosc18.others.benefit.repository.BenefitsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import java.lang.IllegalStateException
import java.math.BigDecimal.TEN
import java.math.BigDecimal.valueOf
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BenefitServiceImplTest {
    private val benefitsRepository: BenefitsRepository = mockk(relaxed = true)
    private val benefitService = BenefitServiceImpl(
        benefitsRepository = benefitsRepository
    )

    @Test
    fun `Must find benefits by accountId`() {
        val accountId = "1"
        val benefitDTO = mockk<BenefitDTO>()

        mockkObject(BenefitDTO)

        every { benefitDTO.accountId } returns "1"
        every { benefitsRepository.findByAccountId(accountId) } returns listOf(mockk())
        every { BenefitDTO.from(any()) } returns benefitDTO

        val benefits = benefitService.findByAccountId(accountId)

        assertEquals(accountId, benefits.first.accountId)
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource("DEBIT", "CREDIT")
    fun `Must update benefit and debit from balance`(operation: Operation) {
        val accountId = "1"
        val updateDto = UpdateBenefitDTO(
            transactionId = accountId,
            accountId = accountId,
            type = FOOD,
            operation = operation,
            value = TEN
        )
        val benefitEntity = mockk<BenefitEntity>(relaxed = true)

        every { benefitEntity.type } returns FOOD
        every { benefitEntity.balance } returns TEN
        every { benefitsRepository.findByAccountId(accountId) } returns listOf(benefitEntity)
        every { benefitsRepository.save(any()) } returns mockk()

        benefitService.update(accountId, updateDto)

        verify(exactly = 1) { benefitsRepository.save(any()) }
    }

    @Test
    fun `Must throw exception if debit value is more than balance`() {
        val accountId = "1"
        val updateDto = UpdateBenefitDTO(
            transactionId = accountId,
            accountId = accountId,
            type = FOOD,
            operation = DEBIT,
            value = valueOf(11)
        )
        val benefitEntity = mockk<BenefitEntity>(relaxed = true)

        every { benefitEntity.type } returns FOOD
        every { benefitEntity.balance } returns TEN
        every { benefitsRepository.findByAccountId(accountId) } returns listOf(benefitEntity)

        assertThrows<IllegalStateException> { benefitService.update(accountId, updateDto) }
    }

    @Test
    fun `Must throw exception if account wallet not be found`() {
        every { benefitsRepository.findByAccountId(any()) } returns emptyList()

        assertThrows<IllegalStateException> { benefitService.update("1", mockk()) }
    }

    @Test
    fun `Must throw exception if benefit wallet not be found for accountId`() {
        val benefitEntity = mockk<BenefitEntity>()
        val updateBenefitDTO = mockk<UpdateBenefitDTO>()

        every { benefitEntity.type } returns MEAL
        every { updateBenefitDTO.type } returns FOOD
        every { benefitsRepository.findByAccountId(any()) } returns listOf(benefitEntity)

        assertThrows<NoSuchElementException> { benefitService.update("1", updateBenefitDTO) }
    }
}