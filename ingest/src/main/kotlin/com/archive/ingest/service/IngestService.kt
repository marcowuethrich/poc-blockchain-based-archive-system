package com.archive.ingest.service

import com.archive.shared.client.DataManagementClient
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

@Service
class IngestService(
    private val dataManagementClient: DataManagementClient,
    @Value("\${archive.ingest.security.verify-fingerprint}") private val verifyFingerprint: Boolean,
    private val mapper: ObjectMapper = jacksonObjectMapper(),
    private val verifier: VerifyHash = VerifyHash(mapper)
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    fun upload(files: Set<MultipartFile>, dto: UploadDto) {
        dto.sips.filter {
            // Verify Fingerprint
            if (verifyFingerprint) {
                this.verifier.verifySIP(it, files)
            } else true
        }.forEach {
            // Execute Action
            when (it.action) {
                SIPAction.ADD -> addSIP(it)
                SIPAction.UPDATE -> updateSIP(it)
                SIPAction.DELETE -> deleteSIP(it)
            }
        }
    }

    private fun addSIP(dto: SIPDto) = dto.aips.forEach { aip ->
        // Save MetaData
        this.dataManagementClient.save(this.convert(dto, aip))

        // Save Files
        TODO("Not yet implemented")

        // Make Blockchain entry

        // Update MetaDataDatabase with blockchain ID
    }


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


    private fun updateSIP(sipDto: SIPDto) {
        TODO("Not yet implemented")
        // Update MetaData

        // Update Files

        // Make Blockchain entry

        // Update MetaDataDatabase with blockchain ID
    }

    private fun deleteSIP(sipDto: SIPDto) {
        TODO("Not yet implemented")
        // Mark MetaData deleted

        // Delete Files
    }
}
