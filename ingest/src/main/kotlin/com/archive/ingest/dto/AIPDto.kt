package com.archive.ingest.dto

import java.io.File

/**
 * Archival Information Package (AIP)
 */
data class AIPDto(
        val dips: Set<DIPDto>,
        val content: File
)
