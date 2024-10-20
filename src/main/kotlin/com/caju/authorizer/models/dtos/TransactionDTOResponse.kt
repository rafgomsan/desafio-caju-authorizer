package com.caju.authorizer.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class TransactionDTOResponse(
    @JsonProperty("transactionId")
    val id: Long,

    @JsonProperty("account")
    val account: String?,

    @JsonProperty("totalAmount")
    val amount: BigDecimal,

    @JsonProperty("merchant")
    val merchant: String,

    @JsonProperty("mcc")
    val mcc: String,

    @JsonProperty("code")
    val code: String,

    )
