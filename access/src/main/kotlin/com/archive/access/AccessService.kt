package com.archive.access

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.dto.AIPDto
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccessService(
    private val storageClient: ArchivalStorageClient,
    private val dataManagementClient: DataManagementClient
) {
    fun getMeta(id: UUID): AIPDto = this.dataManagementClient.getAIP(id)

    fun getContent(id: UUID): ResponseEntity<Resource> {
        val meta = this.dataManagementClient.getAIP(id)
        val content: Resource? = this.storageClient.get(meta.dip.content.id!!).body
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(meta.dip.content.type!!))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + meta.originalContentFileName + "\"")
            .body(content)
    }
}
