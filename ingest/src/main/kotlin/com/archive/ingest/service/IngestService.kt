package com.archive.ingest.service

import com.archive.ingest.client.DataManagementClient
import com.archive.ingest.dto.SIPAction
import com.archive.ingest.dto.SIPDto
import com.archive.ingest.dto.UploadDto
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

    private fun deleteSIP(sipDto: SIPDto) {
        TODO("Not yet implemented")
        // Save MetaData

        // Save Files

        // Make Blockchain entry

        // Update MetaDataDatabase with blockchain ID
    }

    private fun updateSIP(sipDto: SIPDto) {
        TODO("Not yet implemented")
        // Update MetaData

        // Update Files

        // Make Blockchain entry

        // Update MetaDataDatabase with blockchain ID
    }

    private fun addSIP(sipDto: SIPDto) {
        TODO("Not yet implemented")
        // Mark MetaData deleted

        // Delete Files
    }
}
