package com.archive.shared.service

import com.archive.shared.model.dto.HashAlgorithm
import com.archive.shared.model.dto.SIPDto
import com.archive.shared.problem.FileNotFoundInRequestProblem
import com.archive.shared.problem.InputVerificationProblem
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Service
class VerifyService(@Qualifier("objectMapper") private val mapper: ObjectMapper) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun verifySIP(sip: SIPDto, files: List<MultipartFile>) = sip.aips.forEach { aip ->
        this.verifyHash(getFile(files, aip.originalContentFileName).bytes, aip.contentHash, aip.hashAlg)
        this.verifyHash(mapper.writeValueAsBytes(aip.dip), aip.dipHash, aip.hashAlg)
    }

    fun contentToHash(content: ByteArray, algo: HashAlgorithm) = MessageDigest
        .getInstance(algo.instName)
        .digest(content)
        .fold("", { str, it -> str + "%02x".format(it) })

    private fun verifyHash(content: ByteArray, existHash: String, algo: HashAlgorithm) {
        val hash = this.contentToHash(content, algo)
        if (!hash.contentEquals(existHash)) {
            LOGGER.warn("Hash doesn't match. existing: $existHash, calculated: $hash")
            throw InputVerificationProblem()
        }
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String) = files.find { file ->
        file.originalFilename.equals(originalContentFileName, ignoreCase = false)
    } ?: throw FileNotFoundInRequestProblem(originalContentFileName)
}
