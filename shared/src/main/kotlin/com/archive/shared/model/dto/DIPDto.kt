package com.archive.shared.model.dto

import java.time.OffsetDateTime

/**
 * Dissemination Information Package (DIP)
 */
data class DIPDto(
    val content: ContentDto,
    val creation: OffsetDateTime,
    val authorName: String
)
