package com.archive.shared.model.dto

/**
 * Main data transfer object for the upload endpoint.
 * It describes the meta information beside the content files.
 */
data class UploadDto(val sips: Set<SIPDto>)
