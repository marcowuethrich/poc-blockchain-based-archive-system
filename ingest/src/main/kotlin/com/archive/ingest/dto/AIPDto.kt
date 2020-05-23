package com.archive.ingest.dto

/**
 * Archival Information Package (AIP)
 */
data class AIPDto(
        val dips: List<DIPDto>,
        val originalFileName: String,
        val hashAlg: HashAlgorithm = HashAlgorithm.SHA3_256,
        val dipsHashes: List<ByteArray>,
        val contentHash: ByteArray
)

enum class HashAlgorithm(val algName: String) {
    SHA_256("SHA-256"), SHA3_256("SHA3-256");
}
