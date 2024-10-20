package com.caju.authorizer.repositories

import com.caju.authorizer.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {
    fun findByAccountNumber(account: String): Account?
}
