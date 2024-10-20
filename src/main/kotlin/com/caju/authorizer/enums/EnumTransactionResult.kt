package com.caju.authorizer.enums

enum class EnumTransactionResult(val result: String) {
    APPROVED("00"),
    REJECTED("51"),
    OTHERS("07");
}
