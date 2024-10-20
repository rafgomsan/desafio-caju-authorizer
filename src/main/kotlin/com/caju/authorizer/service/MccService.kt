package com.caju.authorizer.service

import com.caju.authorizer.models.Mcc
import com.caju.authorizer.repositories.MccRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MccService(@Autowired var repository: MccRepository) {
    fun getMcc(mcc: String): Mcc? {
        return repository.findByMcc(mcc)
    }
}