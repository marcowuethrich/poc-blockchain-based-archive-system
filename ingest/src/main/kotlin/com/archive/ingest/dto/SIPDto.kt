package com.archive.ingest.dto

/**
 * Submission Information Package (SIP)
 */
data class SIPDto(
        val aips: Set<AIPDto>,
        val fingerprint: String,
        val hashAlg: FingerprintAlgorithm = FingerprintAlgorithm.SHA3,
        val action: SIPAction = SIPAction.ADD,
        val producer: ProducerDto
)

enum class FingerprintAlgorithm {
    SHA2, SHA3
}

enum class SIPAction {
    ADD, UPDATE, DELETE
}
