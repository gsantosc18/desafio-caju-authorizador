package com.github.gsantosc18.authorizer.domain.vo

enum class ResponseCode(val code: String) {
    AUTHORIZED("00"),
    REJECTED("51"),
    INTERNAL_ERROR("07")
}