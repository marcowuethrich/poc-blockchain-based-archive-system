package com.archive.shared.model.dbo

import java.time.Instant
import java.util.*

class BlockchainRef(
    val id: UUID? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val reference: String
)
