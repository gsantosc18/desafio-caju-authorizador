package com.github.gsantosc18.others.account.dto

import java.util.UUID

data class AccountDTO(
    val id: UUID? = null,
    val name: String? = null,
    val active: Boolean? = null
)