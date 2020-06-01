package com.archive.shared.service

import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.dto.*
import com.archive.shared.problem.FileNotFoundInRequestProblem
import com.archive.shared.problem.InputVerificationProblem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Service
class VerifyService(
    private val mapper: ObjectMapper = jacksonObjectMapper(),
    private val sawtoothService: SawtoothService,
    private val dataManagementClient: DataManagementClient
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun verify(sip: SIPDto, files: List<MultipartFile>) = sip.aips.forEach { aip ->
        this.verifyHash(getFile(files, aip.originalContentFileName).bytes, aip.contentHash, aip.hashAlg)
        this.verifyHash(mapper.writeValueAsBytes(aip.dip), aip.dipHash, aip.hashAlg)
    }

    fun verify(aip: AIPDto): AIPDto {
        val state = this.sawtoothService.get(this.dataManagementClient.getBlockchainAddress(aip.id!!))
        // create copy without internal content id
        val dip = DIPDto(
            content = ContentDto(
                name = aip.dip.content.name,
                extension = aip.dip.content.extension,
                type = aip.dip.content.type,
                size = aip.dip.content.size,
                sizeUnit = aip.dip.content.sizeUnit
            ),
            creation = aip.dip.creation,
            authorName = aip.dip.authorName
        )
        this.verifyHash(mapper.writeValueAsBytes(dip), state.dipHash, aip.hashAlg)
        return aip
    }

    private fun prepareAIP(dip: DIPDto): DIPDto = dip.apply {
        dip.content.id = null
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
            throw InputVerificationProblem()
        }
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String) = files.find { file ->
        file.originalFilename.equals(originalContentFileName, ignoreCase = false)
    } ?: throw FileNotFoundInRequestProblem(originalContentFileName)
}
