package com.archive.ingest.service

import com.archive.ingest.exception.FileNotFoundInRequestException
import com.archive.ingest.exception.InputVerificationException
import com.archive.ingest.model.dto.HashAlgorithm
import com.archive.ingest.model.dto.SIPDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Service
class VerifyHash(private val mapper: ObjectMapper) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun verifySIP(sip: SIPDto, files: Set<MultipartFile>): Boolean {
        var ok = true

        sip.aips.forEach { aip ->
            val file = files.find { file -> file.originalFilename.equals(aip.originalContentFileName) }
                    ?: throw FileNotFoundInRequestException(aip.originalContentFileName)

            ok = this.verifyHash(file.bytes, aip.contentHash, aip.hashAlg)
            ok = this.verifyHash(mapper.writeValueAsBytes(aip.dip), aip.dipHash, aip.hashAlg)
        }
        if (!ok) {
            throw InputVerificationException("Verification wasn't successfully!")
        }
        return ok
    }

    private fun verifyHash(content: ByteArray, existHash: ByteArray, algo: HashAlgorithm): Boolean {
        val hash = MessageDigest
                .getInstance(algo.algName)
                .digest(content)
        if (!hash!!.contentEquals(existHash)) {
            LOGGER.warn("Hash doesn't match. existing: $existHash, calculated: $hash")
        }
        return hash.contentEquals(existHash)
    }
}
