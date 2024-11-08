package com.github.gsantosc18.others.account.service

import com.github.gsantosc18.others.account.dto.AccountDTO
import java.util.UUID

interface AccountService {
    fun findById(accountId: UUID): AccountDTO?
}