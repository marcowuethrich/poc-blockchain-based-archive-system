package com.archive.shared.model.dbo

import java.time.Instant
import java.util.*

data class ArchiveObject(
    val id: UUID? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val blockchainRef: BlockchainRef? = null,
    val content: Content,
    val metaData: MetaData,
    val producer: Producer
)
