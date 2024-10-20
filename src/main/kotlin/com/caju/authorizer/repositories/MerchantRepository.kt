package com.caju.authorizer.repositories

import com.caju.authorizer.models.Merchant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MerchantRepository : JpaRepository<Merchant, String> {
    fun findByMerchant(merchant: String): Merchant?
}

