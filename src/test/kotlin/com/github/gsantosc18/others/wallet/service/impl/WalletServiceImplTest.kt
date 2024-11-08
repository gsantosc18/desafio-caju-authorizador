package com.github.gsantosc18.others.wallet.service.impl

import com.github.gsantosc18.others.wallet.dto.UpdateWalletDTO
import com.github.gsantosc18.others.wallet.entity.WalletEntity
import com.github.gsantosc18.others.wallet.enums.Operation
import com.github.gsantosc18.others.wallet.repository.WalletRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WalletServiceImplTest {

    private val walletRepository: WalletRepository = mockk()
    private val walletServiceImpl = WalletServiceImpl(walletRepository)

    @Test
    fun `findByAccountId should return WalletDTO when wallet exists`() {
        val accountId = "123"
        val walletEntity = WalletEntity(
            id = UUID.randomUUID(),
            accountId = accountId,
            balance = BigDecimal.TEN
        )
        every { walletRepository.findByAccountId(accountId) } returns walletEntity

        val result = walletServiceImpl.findByAccountId(accountId)

        assertNotNull(result)
        assertEquals(accountId, result?.accountId)
        assertEquals(BigDecimal.TEN, result?.balance)
        verify { walletRepository.findByAccountId(accountId) }
    }

    @Test
    fun `findByAccountId should return null when wallet does not exist`() {
        val accountId = "123"
        every { walletRepository.findByAccountId(accountId) } returns null

        val result = walletServiceImpl.findByAccountId(accountId)

        assertNull(result)
        verify { walletRepository.findByAccountId(accountId) }
    }

    @Test
    fun `update should debit from wallet when balance is sufficient`() {
        val accountId = "123"
        val initialBalance = BigDecimal(100)
        val walletEntity = WalletEntity(
            id = UUID.randomUUID(),
            accountId = accountId,
            balance = initialBalance
        )
        val updateWalletDTO = UpdateWalletDTO(
            operation = Operation.DEBIT,
            value = BigDecimal(50),
            accountId = accountId,
            transactionId = ""
        )

        every { walletRepository.findByAccountId(accountId) } returns walletEntity
        every { walletRepository.save(any()) } returns walletEntity.copy(balance = BigDecimal(50))

        walletServiceImpl.update(accountId, updateWalletDTO)

        verify { walletRepository.findByAccountId(accountId) }
        verify { walletRepository.save(match { it.balance == BigDecimal(50) })}
    }

    @Test
    fun `update should throw exception when debit causes negative balance`() {
        val accountId = "123"
        val walletEntity = WalletEntity(
            id = UUID.randomUUID(),
            accountId = accountId,
            balance = BigDecimal.TEN
        )
        val updateWalletDTO = UpdateWalletDTO(
            operation = Operation.DEBIT,
            value = BigDecimal(50),
            accountId = accountId,
            transactionId = ""
        )

        every { walletRepository.findByAccountId(accountId) } returns walletEntity

        val exception = assertThrows<IllegalStateException> { walletServiceImpl.update(accountId, updateWalletDTO) }

        assertEquals("Balance cannot be less than zero", exception.message)
        verify { walletRepository.findByAccountId(accountId) }
    }

    @Test
    fun `update should credit to wallet`() {
        val accountId = "123"
        val initialBalance = BigDecimal(100)
        val walletEntity = WalletEntity(
            id = UUID.randomUUID(),
            accountId = accountId,
            balance = initialBalance
        )
        val updateWalletDTO = UpdateWalletDTO(
            operation = Operation.CREDIT,
            value = BigDecimal(50),
            accountId = accountId,
            transactionId = ""
        )
        val entityCapturingSlot = slot<WalletEntity>()

        every { walletRepository.findByAccountId(accountId) } returns walletEntity
        every { walletRepository.save(capture(entityCapturingSlot)) } returns walletEntity.copy(balance = BigDecimal(150))

        walletServiceImpl.update(accountId, updateWalletDTO)

        verify { walletRepository.findByAccountId(accountId) }
        verify { walletRepository.save(match{ it.balance == BigDecimal(150) }) }
    }

    @Test
    fun `update should throw exception when wallet is not found`() {
        val accountId = "123"

        every { walletRepository.findByAccountId(accountId) } returns null

        val exception = assertThrows<IllegalStateException> {
            walletServiceImpl.update(accountId, mockk())
        }

        assertEquals("Wallet not found", exception.message)
        verify { walletRepository.findByAccountId(accountId) }
    }
}
