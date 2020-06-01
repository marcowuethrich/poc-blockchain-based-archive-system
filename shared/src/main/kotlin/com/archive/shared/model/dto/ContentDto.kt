package com.archive.shared.model.dto

import java.util.*

/**
 * Information about the content file
 */
data class ContentDto(
    var id: UUID? = null,
    val name: String?,
    val extension: String?,
    val type: String?,
    val size: Int?,
    val sizeUnit: String?
)
