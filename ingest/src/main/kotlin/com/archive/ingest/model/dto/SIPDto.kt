package com.archive.ingest.model.dto

/**
 * Submission Information Package (SIP)
 */
data class SIPDto(
        val aips: List<AIPDto>,
        val action: SIPAction = SIPAction.ADD,
        val producer: ProducerDto
)

enum class SIPAction {
    ADD, UPDATE, DELETE
}
