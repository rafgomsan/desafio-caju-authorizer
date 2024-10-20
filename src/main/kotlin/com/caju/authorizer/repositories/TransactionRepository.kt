package com.caju.authorizer.repositories

import com.caju.authorizer.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long>
