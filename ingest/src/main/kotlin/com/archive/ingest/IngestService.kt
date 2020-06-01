package com.archive.ingest

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.ModelConverter
import com.archive.shared.model.dto.*
import com.archive.shared.problem.AIPUpdateProblem
import com.archive.shared.problem.FileNotFoundInRequestProblem
import com.archive.shared.service.SawtoothService
import com.archive.shared.service.VerifyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.util.*

/**
 * Ingest service
 * Implements the logic for importing the sip's form producer
 */
@Service
class IngestService(
    @Value("\${archive.ingest.security.verify-fingerprint}") private val verifyFingerprint: Boolean,
    private val dataManagementClient: DataManagementClient,
    private val archivalStorageClient: ArchivalStorageClient,
    private val verifier: VerifyService,
    private val sawtoothService: SawtoothService,
    private val converter: ModelConverter
) {

    fun uploadSIP(files: List<MultipartFile>, dto: UploadDto) = with(dto) {
        if (verifyFingerprint && dto.sip.action != SIPAction.DELETE) {
            verifier.verify(sip, files)
        }
        sip.aips.forEach { aip ->
            // Execute Action
            when (sip.action) {
                SIPAction.ADD -> addAIP(sip, aip, getFile(files, aip.originalContentFileName!!))
                SIPAction.UPDATE -> updateAIP(sip, aip, getFile(files, aip.originalContentFileName!!))
                SIPAction.DELETE -> deleteAIP(aip.id!!)
            }
        }
    }

    private fun addAIP(dto: SIPDto, aip: AIPDto, file: MultipartFile?) {
        if (file == null) {
            throw FileNotFoundInRequestProblem("Filename: ${aip.originalContentFileName}")
        }
        // Save MetaData
        val archiveObject = this.dataManagementClient.save(this.converter.dtoToDbo(dto, aip))

        with(archiveObject) {
            try {
                // Save File
                archivalStorageClient.add(content!!.id, file)

                // Make Blockchain entry
                val address = sawtoothService.submit(
                    TransactionDto(
                        blockchainAddress = sawtoothService.createRandomAddress(),
                        contentHash = content!!.fingerprint.toString(),
                        dipHash = aip.dipHash!!
                    )
                )

                // TODO check if transaction in blockchain was committed

                // Update MetaDataDatabase with blockchain Address
                dataManagementClient.updateBlockchainAddress(id, address)
            } catch (e: Exception) {
                dataManagementClient.delete(id)
                archivalStorageClient.delete(content!!.id)
                throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Could not add AIP, reason: $e")
            }
            throw Problem.valueOf(Status.OK, "aipId: $id")

        }
    }


    private fun updateAIP(sipDto: SIPDto, aip: AIPDto, file: MultipartFile?) {
        if (aip.id == null) {
            throw AIPUpdateProblem("AIP id is null")
        } else {

            try {
                // Update MetaData
                val archiveObject = this.dataManagementClient.update(aip.id!!, this.converter.dtoToDbo(sipDto, aip))

                // Update Files
                if (file != null) {
                    this.archivalStorageClient.replace(archiveObject.content!!.id, file)
                }

                // Make Blockchain entry
                sawtoothService.submit(
                    TransactionDto(
                        // only the internal ref, the namespace will be added by the sawtooth service
                        blockchainAddress = archiveObject.blockchainRef!!.reference.toString().substring(6),
                        contentHash = archiveObject.content!!.fingerprint!!,
                        dipHash = archiveObject.metaData!!.fingerprint!!,
                        action = TransactionAction.REPLACE
                    )
                )
                // TODO check if transaction in blockchain was committed
            } catch (e: Exception) {
                throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "$e")
            }
            throw Problem.valueOf(Status.OK, "aipId: ${aip.id}")
        }
    }

    private fun deleteAIP(id: UUID) {
        val meta = this.dataManagementClient.getAIP(id)

        // Mark MetaData deleted
        this.dataManagementClient.delete(meta.id!!)

        // Delete Files
        this.archivalStorageClient.delete(meta.dip!!.content.id!!)
    }

    private fun getFile(files: List<MultipartFile>, originalContentFileName: String): MultipartFile? =
        files.find { file ->
            file.originalFilename.equals(originalContentFileName, ignoreCase = false)
        }
}
