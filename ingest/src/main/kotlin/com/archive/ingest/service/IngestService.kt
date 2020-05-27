package com.archive.ingest.service

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.exception.AIPUpdateException
import com.archive.shared.exception.FileNotFoundInRequestException
import com.archive.shared.model.dbo.ArchiveObject
import com.archive.shared.model.dbo.Content
import com.archive.shared.model.dbo.MetaData
import com.archive.shared.model.dbo.Producer
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.model.dto.SIPAction
import com.archive.shared.model.dto.SIPDto
import com.archive.shared.model.dto.UploadDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class IngestService(
    @Value("\${archive.ingest.security.verify-fingerprint}") private val verifyFingerprint: Boolean,
    private val dataManagementClient: DataManagementClient,
    private val archivalStorageClient: ArchivalStorageClient,
    private val mapper: ObjectMapper = jacksonObjectMapper(),
    private val verifier: VerifyHash = VerifyHash(mapper)
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun upload(files: List<MultipartFile>, dto: UploadDto) = with(dto) {
        if (verifyFingerprint) {
            verifier.verifySIP(sip, files)
        }
        sip.aips.forEach { aip ->
            // Execute Action
            when (sip.action) {
                SIPAction.ADD -> addAPI(sip, aip, getFile(files, aip.originalContentFileName))
                SIPAction.UPDATE -> updateAPI(sip, aip, getFile(files, aip.originalContentFileName))
                SIPAction.DELETE -> deleteAPI(aip.id!!)
            }
        }
    }

    private fun addAPI(dto: SIPDto, aip: AIPDto, file: MultipartFile) {
        // Save MetaData
        val archiveObject = this.dataManagementClient.save(this.convert(dto, aip))

        archiveObject.content.id?.let {
            // Save File
            this.archivalStorageClient.add(it, file)

            // Make Blockchain entry
            TODO("Not yet implemented")

            // Update MetaDataDatabase with blockchain ID
            TODO("Not yet implemented")
        }

    }

    private fun updateAPI(sipDto: SIPDto, aip: AIPDto, file: MultipartFile) {
        if (aip.id == null) {
            throw AIPUpdateException("AIP id is null")
        } else {

            // Update MetaData
            val archiveObject = this.dataManagementClient.update(aip.id!!, this.convert(sipDto, aip))
            if (archiveObject.id == null) {
                throw AIPUpdateException("Could not load AIP Metadata from database with id ${aip.id}")
            } else {

                // Update Files
                this.archivalStorageClient.replace(archiveObject.id!!, file)

                // Make Blockchain entry
                TODO("Not yet implemented")

                // Update MetaDataDatabase with blockchain ID
                TODO("Not yet implemented")
            }
        }
    }

    private fun deleteAPI(id: UUID) {
        // Mark MetaData deleted
        this.dataManagementClient.delete(id)

        // Delete Files
        this.archivalStorageClient.delete(id)
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String) = files.find { file ->
        file.originalFilename.equals(originalContentFileName, ignoreCase = false)
    } ?: throw FileNotFoundInRequestException(originalContentFileName)

    private fun convert(sip: SIPDto, aip: AIPDto) = ArchiveObject(
        content = Content(
            name = aip.dip.content.name,
            extension = aip.dip.content.extension,
            type = aip.dip.content.type,
            size = aip.dip.content.size,
            sizeUnit = aip.dip.content.sizeUnit
        ),
        metaData = MetaData(
            creation = aip.dip.creation,
            authorName = aip.dip.authorName
        ),
        producer = Producer(
            name = sip.producer.name,
            uuid = sip.producer.uuid
        )
    )
}
