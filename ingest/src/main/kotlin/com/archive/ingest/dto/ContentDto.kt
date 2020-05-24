package com.archive.ingest.dto

/**
 * Information about the content file
 */
data class ContentDto(
        val name: String,
        val extension: String,
        val type: String,
        val size: Number,
        val sizeUnit: String
)
