package com.archive.ingest.model.dbo

import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class MetaData(
        val id: UUID? = null,
        val createdAt: Instant? = null,
        val updatedAt: Instant? = null,
        val creation: OffsetDateTime,
        val authorName: String
)
