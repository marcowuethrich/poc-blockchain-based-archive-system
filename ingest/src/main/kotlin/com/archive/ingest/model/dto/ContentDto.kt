package com.archive.ingest.model.dto

/**
 * Information about the content file
 */
data class ContentDto(
        val name: String,
        val extension: String,
        val type: String,
        val size: Int,
        val sizeUnit: String
)
