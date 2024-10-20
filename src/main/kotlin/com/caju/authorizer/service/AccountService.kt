package com.caju.authorizer.service

import com.caju.authorizer.models.Account
import com.caju.authorizer.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService(@Autowired var repository: AccountRepository) {

    fun getAccount(account: String): Account? {
        return repository.findByAccountNumber(account)
    }

    fun updateAccountNumber(account: Account): Account? {
       return repository.save(account)
    }
}