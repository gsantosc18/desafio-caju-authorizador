package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode

interface PaymentHandler {
    fun handle(request: RequestParams, transaction: Transaction): ResponseCode

    fun setNext(handler: PaymentHandler): PaymentHandler?
}