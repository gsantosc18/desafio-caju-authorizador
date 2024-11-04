package com.github.gsantosc18.authorizer.application.usecase

import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentRequest
import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentResponse

interface ProcessPaymentUseCase {
    fun execute(request: ProcessPaymentRequest): ProcessPaymentResponse
}