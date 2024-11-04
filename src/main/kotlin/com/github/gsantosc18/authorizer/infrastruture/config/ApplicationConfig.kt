package com.github.gsantosc18.authorizer.infrastruture.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.gsantosc18.authorizer.application.usecase.ProcessPaymentUseCase
import com.github.gsantosc18.authorizer.application.usecase.chain.BenefitPaymentHandler
import com.github.gsantosc18.authorizer.application.usecase.chain.PaymentHandler
import com.github.gsantosc18.authorizer.application.usecase.chain.WalletPaymentHandler
import com.github.gsantosc18.authorizer.application.usecase.impl.ProcessPaymentUseCaseImpl
import com.github.gsantosc18.authorizer.domain.client.AccountClient
import com.github.gsantosc18.authorizer.domain.client.BenefitClient
import com.github.gsantosc18.authorizer.domain.client.MerchantClient
import com.github.gsantosc18.authorizer.domain.client.WalletClient
import com.github.gsantosc18.authorizer.domain.repository.TransactionRepository
import com.github.gsantosc18.authorizer.infrastruture.serializer.ResponseCodeSerializer
import feign.Logger
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig(
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun paymentHandler(
        benefitClient: BenefitClient,
        walletClient: WalletClient
    ): PaymentHandler {
        val benefitPaymentHandler = BenefitPaymentHandler(benefitClient)
        val walletPaymentHandler = WalletPaymentHandler(walletClient)
        benefitPaymentHandler.setNext(walletPaymentHandler)
        return benefitPaymentHandler
    }

    @Bean
    fun processPaymentUseCase(
        transactionRepository: TransactionRepository,
        accountClient: AccountClient,
        merchantClient: MerchantClient,
        paymentHandler: PaymentHandler
    ): ProcessPaymentUseCase =
        ProcessPaymentUseCaseImpl(
            transactionRepository,
            accountClient,
            merchantClient,
            paymentHandler
        )

    @PostConstruct
    fun configSerializer() {
        SimpleModule()
            .apply{ addSerializer(ResponseCodeSerializer()) }
            .also(objectMapper::registerModule)
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}