package com.caju.authorizer.repositories

import com.caju.authorizer.models.Mcc
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MccRepository : JpaRepository<Mcc, String> {
    fun findByMcc(mcc: String): Mcc?
}

