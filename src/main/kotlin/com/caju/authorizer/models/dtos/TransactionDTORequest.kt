package com.caju.authorizer.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class TransactionDTORequest(

    @JsonProperty("account")
    val account: String,

    @Transient
    @JsonProperty("account_id")
    val accountId: Long?,

    @Transient
    @JsonProperty("merchant_id")
    val merchantId: Long?,

    @JsonProperty("totalAmount")
    val amount: BigDecimal,

    @JsonProperty("merchant")
    val merchant: String,

    @JsonProperty("mcc")
    val mcc: String,

    @Transient
    @JsonProperty("code")
    val code: String = null.toString(),

    )
