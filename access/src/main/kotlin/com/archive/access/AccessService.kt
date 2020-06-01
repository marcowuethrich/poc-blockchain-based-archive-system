package com.archive.access

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.dto.AIPDto
import com.archive.shared.service.VerifyService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

/**
 * AccessService for managing the logic
 */
@Service
class AccessService(
    private val storageClient: ArchivalStorageClient,
    private val dataManagementClient: DataManagementClient,
    private val verifier: VerifyService
) {
    fun getMeta(id: UUID): AIPDto = this.verifier.verify(this.dataManagementClient.getAIP(id))

    fun getContent(id: UUID): ResponseEntity<Resource> {
        val meta = this.verifier.verify(this.dataManagementClient.getAIP(id))
        val content: Resource = this.verifier.verify(meta, this.storageClient.get(meta.dip.content.id!!).body)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(meta.dip.content.type!!))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + meta.originalContentFileName + "\"")
            .body(content)
    }
}
