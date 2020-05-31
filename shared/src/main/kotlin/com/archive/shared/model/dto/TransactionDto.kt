package com.archive.shared.model.dto

data class TransactionDto(
    val blockchainAddress: String,
    val familyName: String = "intkey",
    val familyVersion: String = "1.0",
    val contentHash: String,
    val dipHash: String
)
