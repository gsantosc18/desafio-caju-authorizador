package com.github.gsantosc18.authorizer.application.dto

import com.github.gsantosc18.authorizer.domain.vo.ResponseCode

data class ProcessPaymentResponse(
    val code: ResponseCode
)
