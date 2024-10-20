package com.caju.authorizer.models

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.sql.Timestamp
import kotlin.jvm.Transient


@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var accountId: Long? = null,

    @Column(name = "balance_food")
    val balanceFood: BigDecimal,

    @Column(name = "balance_meal")
    val balanceMeal: BigDecimal,

    @Column(name = "balance_cash")
    val balanceCash: BigDecimal,

    @Column(name = "account", nullable = false)
    val accountNumber: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Timestamp,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Timestamp,
) {

    @Transient
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private val merchant: Merchant? = null

}
