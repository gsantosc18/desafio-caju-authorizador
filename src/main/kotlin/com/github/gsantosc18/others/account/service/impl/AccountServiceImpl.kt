package com.github.gsantosc18.others.account.service.impl

import com.github.gsantosc18.others.account.dto.AccountDTO
import com.github.gsantosc18.others.account.repository.AccountRepository
import com.github.gsantosc18.others.account.service.AccountService
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val repository: AccountRepository
): AccountService {
    override fun findById(accountId: UUID): AccountDTO? {
        return repository.findByIdOrNull(accountId)?.let{
            AccountDTO(
                id = it.id,
                name = it.name,
                active = it.active
            )
        }
    }

}