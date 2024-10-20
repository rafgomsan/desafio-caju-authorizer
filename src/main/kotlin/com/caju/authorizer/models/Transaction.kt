package com.caju.authorizer.models

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.sql.Timestamp

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = true)
    val transactionId: Long?,

    @Column(name = "account_id")
    val accountId: Long?,

    @Column(name = "merchant_id")
    val merchantId: Long?,

    @Column(name = "account")
    val account: String,

    @Column(name = "total_amount")
    val amount: BigDecimal,

    @Column(name = "merchant", nullable = false)
    val merchant: String,

    @Column(name = "mcc")
    val mcc: String,

    @Column(name = "code")
    val code: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Timestamp? = null,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Timestamp? = null


)
