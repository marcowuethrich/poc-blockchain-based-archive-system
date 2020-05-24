package com.archive.ingest.dto

/**
 * Archival Information Package (AIP)
 */
data class AIPDto(
        val dip: DIPDto,
        val dipHash: ByteArray,
        val originalContentFileName: String,
        val contentHash: ByteArray,
        val hashAlg: HashAlgorithm = HashAlgorithm.SHA3_256
)

enum class HashAlgorithm(val algName: String) {
    SHA_256("SHA-256"), SHA3_256("SHA3-256");
}
