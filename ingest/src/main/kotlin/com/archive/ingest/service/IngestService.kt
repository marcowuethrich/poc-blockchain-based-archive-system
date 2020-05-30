package com.archive.ingest.service

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.ModelConverter
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.model.dto.SIPAction
import com.archive.shared.model.dto.SIPDto
import com.archive.shared.model.dto.UploadDto
import com.archive.shared.problem.AIPUpdateProblem
import com.archive.shared.problem.FileNotFoundInRequestProblem
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.util.*

@Service
class IngestService(
    @Value("\${archive.ingest.security.verify-fingerprint}") private val verifyFingerprint: Boolean,
    private val dataManagementClient: DataManagementClient,
    private val archivalStorageClient: ArchivalStorageClient,
    @Qualifier("objectMapper") private val mapper: ObjectMapper,
    private val verifier: VerifyService = VerifyService(mapper),
    private val converter: ModelConverter
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun uploadSIP(files: List<MultipartFile>, dto: UploadDto) = with(dto) {
        if (verifyFingerprint) {
            verifier.verifySIP(sip, files)
        }
        sip.aips.forEach { aip ->
            // Execute Action
            when (sip.action) {
                SIPAction.ADD -> addAIP(sip, aip, getFile(files, aip.originalContentFileName))
                SIPAction.UPDATE -> updateAIP(sip, aip, getFile(files, aip.originalContentFileName))
                SIPAction.DELETE -> deleteAIP(aip.id!!)
            }
        }
    }

    private fun addAIP(dto: SIPDto, aip: AIPDto, file: MultipartFile) {
        // Save MetaData
        val archiveObject = this.dataManagementClient.save(this.converter.dtoToDbo(dto, aip))

        with(archiveObject) {
            try {
                // Save File
                archivalStorageClient.add(content!!.id, file)

                // Make Blockchain entry
                // TODO("Blockchain connection")
                val ref = UUID.randomUUID().toString()

                // Update MetaDataDatabase with blockchain ID
                dataManagementClient.updateBlockchainRef(id, ref)
            } catch (e: Exception) {
                dataManagementClient.delete(id)
                archivalStorageClient.delete(id)
                throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Could not add AIP, reason: $e")
            }
            throw Problem.valueOf(Status.OK, id.toString())

        }
    }

    private fun updateAIP(sipDto: SIPDto, aip: AIPDto, file: MultipartFile) {
        if (aip.id == null) {
            throw AIPUpdateProblem("AIP id is null")
        } else {

            // Update MetaData
            val archiveObject = this.dataManagementClient.update(aip.id!!, this.converter.dtoToDbo(sipDto, aip))

            // Update Files
            this.archivalStorageClient.replace(archiveObject.id, file)

            // Make Blockchain entry
            TODO("Not yet implemented")

            // Update MetaDataDatabase with blockchain ID
            TODO("Not yet implemented")
        }
    }

    private fun deleteAIP(id: UUID) {
        // Mark MetaData deleted
        this.dataManagementClient.delete(id)

        // Delete Files
        this.archivalStorageClient.delete(id)
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String) = files.find { file ->
        file.originalFilename.equals(originalContentFileName, ignoreCase = false)
    } ?: throw FileNotFoundInRequestProblem(originalContentFileName)
}
