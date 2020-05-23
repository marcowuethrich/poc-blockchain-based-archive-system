package com.archive.ingest.dto

import java.time.OffsetDateTime

/**
 * Dissemination Information Package (DIP)
 */
data class DIPDto(
        val contentFile: ContentFileDto,
        val creation: OffsetDateTime,
        val authorName: String
)
