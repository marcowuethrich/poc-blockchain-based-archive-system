package com.archive.shared.model.dto

import java.util.*

/**
 * Archival Information Package (AIP)
 */
data class AIPDto(
    val id: UUID?,
    val dip: DIPDto,
    val dipHash: String,
    val originalContentFileName: String,
    val contentHash: String,
    val hashAlg: HashAlgorithm = HashAlgorithm.SHA3_256
)

enum class HashAlgorithm(val instName: String) {
    SHA_256("SHA-256"),
    SHA3_256("SHA3-256");
}
