package com.archive.ingest.model.dbo

import java.time.Instant
import java.util.*

class Content(
        val id: UUID? = null,
        val createdAt: Instant? = null,
        val updatedAt: Instant? = null,
        val name: String,
        val extension: String,
        val type: String,
        val size: Int,
        val sizeUnit: String
)
