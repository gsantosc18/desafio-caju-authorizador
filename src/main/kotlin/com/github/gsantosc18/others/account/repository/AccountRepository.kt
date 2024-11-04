package com.github.gsantosc18.others.account.repository

import com.github.gsantosc18.others.account.entity.AccountEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<AccountEntity, UUID>