package com.caju.authorizer.models

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp

@Entity
@Table(name = "mccs")
data class Mcc(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mcc_id")
    val mccId: Long,

    @Column(name = "mcc", nullable = false)
    val mcc: String,

    @Column(name = "assigned_to", nullable = false)
    val assignedTo: String,

    @Transient
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private val merchant: Merchant? = null,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Timestamp,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Timestamp

)
