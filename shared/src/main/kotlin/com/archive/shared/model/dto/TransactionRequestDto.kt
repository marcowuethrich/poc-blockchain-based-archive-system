package com.archive.shared.model.dto

data class TransactionDto(
    val blockchainAddress: String, // Must be at least 64 digits or more
    val familyName: String = "archive",
    val familyVersion: String = "1.0",
    val contentHash: String,
    val dipHash: String,
    val action: TransactionAction = TransactionAction.CREATE
)

enum class TransactionAction(val action: String) {
    CREATE("create"), REPLACE("replace")
}
