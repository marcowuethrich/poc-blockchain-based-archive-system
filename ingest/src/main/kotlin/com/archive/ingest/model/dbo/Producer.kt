package com.archive.ingest.model.dbo

import java.time.Instant
import java.util.*

class Producer(
        val id: UUID? = null,
        val createdAt: Instant? = null,
        val updatedAt: Instant? = null,
        val name: String,
        val uuid: UUID
)
