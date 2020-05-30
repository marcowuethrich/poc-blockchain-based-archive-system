package com.archive.shared.model.dto

/**
 * Dissemination Information Package (DIP)
 */
data class DIPDto(
    val content: ContentDto,
    val creation: String,
    val authorName: String
)
