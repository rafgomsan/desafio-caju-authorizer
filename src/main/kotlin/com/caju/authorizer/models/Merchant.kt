package com.caju.authorizer.models

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp

@Entity
@Table(name = "merchants")
data class Merchant(
    @Id
    @Column(name = "merchant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var merchantId: Long? = null,

    @NotNull
    @Column(name = "merchant_name", nullable = false)
    val merchant: String,

    @Column(name = "mccs", nullable = false)
    val mccs: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Timestamp,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Timestamp

) {
    override fun toString(): String {
        return "Merchant(id=$merchantId, merchant='$merchant', mccs='$mccs', account=$account, createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id", referencedColumnName = "merchant_id")
    var account: MutableSet<Account> = mutableSetOf()

}
