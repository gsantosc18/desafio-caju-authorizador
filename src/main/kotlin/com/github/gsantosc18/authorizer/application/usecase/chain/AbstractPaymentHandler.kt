package com.github.gsantosc18.authorizer.application.usecase.chain

import com.github.gsantosc18.authorizer.domain.entity.Transaction
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode.REJECTED

abstract class AbstractPaymentHandler: PaymentHandler {
    private var nextHandler: PaymentHandler? = null

    override fun setNext(handler: PaymentHandler): PaymentHandler? {
        nextHandler = handler
        return handler
    }

    override fun handle(request: RequestParams, transaction: Transaction): ResponseCode =
        nextHandler?.handle(request, transaction) ?: REJECTED

}