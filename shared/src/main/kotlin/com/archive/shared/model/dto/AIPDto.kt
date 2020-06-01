package com.archive.shared.model.dto

import java.util.*

/**
 * Archival Information Package (AIP)
 */
data class AIPDto(
    var id: UUID? = null,
    val dip: DIPDto? = null,
    val dipHash: String? = null,
    val originalContentFileName: String? = null,
    val contentHash: String? = null,
    val hashAlg: HashAlgorithm = HashAlgorithm.SHA3_256
)

enum class HashAlgorithm(val instName: String) {
    SHA_256("SHA-256"),
    SHA_512("SHA-512"),
    SHA3_256("SHA3-256");
}
