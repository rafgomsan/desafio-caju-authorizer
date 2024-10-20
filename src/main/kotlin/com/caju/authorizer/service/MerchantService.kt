package com.caju.authorizer.service

import com.caju.authorizer.models.Merchant
import com.caju.authorizer.repositories.MerchantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MerchantService(@Autowired var repository: MerchantRepository) {

    fun getMerchant(merchant: String): Merchant? {
        return repository.findByMerchant(merchant)
    }

}