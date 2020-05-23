package com.archive.ingest.service

import com.archive.ingest.dto.AIPDto
import com.archive.ingest.dto.SIPDto
import com.archive.ingest.exception.FileNotFoundInRequestException
import com.archive.ingest.exception.InputVerificationException
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
            ok = this.verifyFileHash(aip, files)
            ok = this.verifyDIPHashes(aip)
        }
        if (!ok) {
            throw InputVerificationException("Verification wasn't successfully!")
        }
        return ok
    }

    private fun verifyDIPHashes(aip: AIPDto): Boolean {
        var ok = true
        for ((index, dip) in aip.dips.withIndex()) {
            val hash = MessageDigest
                    .getInstance(aip.hashAlg.algName)
                    .digest(mapper.writeValueAsBytes(dip))

            ok = hash!!.contentEquals(aip.dipsHashes[index])
            if (!ok) {
                LOGGER.warn("DIP Hash doesn't match. request: ${aip.dipsHashes[index]}, calculated: $hash")
            }
        }
        return ok
    }

    private fun verifyFileHash(aip: AIPDto, files: Set<MultipartFile>): Boolean {
        val file = files.find { file -> file.originalFilename.equals(aip.originalFileName) }
                ?: throw FileNotFoundInRequestException(aip.originalFileName)
        val hash = MessageDigest
                .getInstance(aip.hashAlg.algName)
                .digest(file.bytes)
        if (!hash!!.contentEquals(aip.contentHash)) {
            LOGGER.warn("File Hash doesn't match. request: ${aip.contentHash}, calculated: $hash")
        }
        return hash.contentEquals(aip.contentHash)
    }
}
