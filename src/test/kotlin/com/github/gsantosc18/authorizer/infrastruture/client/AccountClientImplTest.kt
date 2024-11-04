package com.github.gsantosc18.authorizer.infrastruture.client

import com.github.gsantosc18.authorizer.domain.vo.Account
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.server.ResponseStatusException

class AccountClientImplTest {

    private val accountHttp: AccountHttp = mockk()

    private val accountClient = AccountClientImpl(accountHttp)

    @Test
    fun `should return account when account exists`() {
        val accountId = "123"
        val expectedAccount = Account(accountId, "John Doel", true)

        every { accountHttp.findById(accountId) } returns expectedAccount

        val account = accountClient.findById(accountId)

        assertNotNull(account)
        assertEquals(expectedAccount, account)
    }

    @Test
    fun `should return null when account does not exist (404 response)`() {
        val accountId = "123"

        every { accountHttp.findById(accountId) } throws ResponseStatusException(NOT_FOUND)

        assertThrows<ResponseStatusException> { accountClient.findById(accountId) }
    }

    @Test
    fun `should throw exception for non-404 errors`() {
        val accountId = "123"

        every { accountHttp.findById(accountId) } throws ResponseStatusException(INTERNAL_SERVER_ERROR)

        assertThrows<ResponseStatusException> { accountClient.findById(accountId) }
    }
}
