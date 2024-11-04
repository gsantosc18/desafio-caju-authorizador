package com.github.gsantosc18.authorizer.infrastructure.api

import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentRequest
import com.github.gsantosc18.authorizer.application.dto.ProcessPaymentResponse
import com.github.gsantosc18.authorizer.application.usecase.ProcessPaymentUseCase
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/payment")
class PaymentController(
    private val processPaymentUseCase: ProcessPaymentUseCase
) {
    @PostMapping
    fun processPayment(
        @RequestBody request: ProcessPaymentRequest
    ): ResponseEntity<ProcessPaymentResponse> =
        processPaymentUseCase.execute(request)
            .let{ ResponseEntity.ok(it) }

    @ExceptionHandler(Throwable::class)
    fun exceptionHandler(ex: Throwable): ResponseEntity<ProcessPaymentResponse> =
        ResponseEntity.ok(ProcessPaymentResponse(ResponseCode.INTERNAL_ERROR))
}