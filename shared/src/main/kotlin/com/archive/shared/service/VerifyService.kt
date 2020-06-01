package com.archive.shared.service

import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.ModelConverter
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.model.dto.HashAlgorithm
import com.archive.shared.model.dto.SIPDto
import com.archive.shared.problem.FileNotFoundInRequestProblem
import com.archive.shared.problem.InputValidationProblem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

/**
 * Verify service
 * Implements the validation and verification logic
 */
@Service
class VerifyService(
    private val mapper: ObjectMapper = jacksonObjectMapper(),
    private val sawtoothService: SawtoothService,
    private val dataManagementClient: DataManagementClient,
    private val converter: ModelConverter
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun verify(sip: SIPDto, files: List<MultipartFile>) = sip.aips.forEach { aip ->
        this.verifyHash(getFile(files, aip.originalContentFileName!!).bytes, aip.contentHash!!, aip.hashAlg)
        this.verifyHash(
            mapper.writeValueAsBytes(this.converter.createVerifiableAIP(aip.dip!!)),
            aip.dipHash!!,
            aip.hashAlg
        )
    }

    fun verify(aip: AIPDto): AIPDto {
        val state = this.sawtoothService.get(this.dataManagementClient.getBlockchainAddress(aip.id!!))
        this.verifyHash(
            mapper.writeValueAsBytes(this.converter.createVerifiableAIP(aip.dip!!)),
            state.dipHash,
            aip.hashAlg
        )
        return aip
    }

    fun verify(aip: AIPDto, content: Resource?): Resource {
        val state = this.sawtoothService.get(this.dataManagementClient.getBlockchainAddress(aip.id!!))
        this.verifyHash(IOUtils.toByteArray(content!!.inputStream), state.contentHash, aip.hashAlg)
        return content
    }

    fun contentToHash(content: ByteArray, algo: HashAlgorithm) = MessageDigest
        .getInstance(algo.instName)
        .digest(content)
        .fold("", { str, it -> str + "%02x".format(it) })

    fun verifyHash(content: ByteArray, existHash: String, algo: HashAlgorithm) {
        val hash = this.contentToHash(content, algo)
        if (!hash.contentEquals(existHash)) {
            LOGGER.warn("Hash doesn't match. existing: $existHash, calculated: $hash")
            throw InputValidationProblem()
        }
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String) = files.find { file ->
        file.originalFilename.equals(originalContentFileName, ignoreCase = false)
    } ?: throw FileNotFoundInRequestProblem(originalContentFileName)
}
