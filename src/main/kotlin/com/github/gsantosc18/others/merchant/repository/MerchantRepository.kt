package com.github.gsantosc18.others.merchant.repository

import com.github.gsantosc18.others.merchant.entity.MerchantEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantRepository: JpaRepository<MerchantEntity, UUID> {
    fun findByName(name: String): MerchantEntity?
}