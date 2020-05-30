package com.archive.access

import com.archive.shared.client.ArchivalStorageClient
import com.archive.shared.client.DataManagementClient
import com.archive.shared.model.dto.AIPDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccessService(
    private val storageClient: ArchivalStorageClient,
    private val dataManagementClient: DataManagementClient
) {
    fun getMeta(id: UUID): AIPDto = this.dataManagementClient.getAIP(id)

    fun getContent(id: UUID) {
        val meta = this.dataManagementClient.getAIP(id)
        val content = this.storageClient.get(meta.dip.content.id!!)
        content.readBytes()
    }
}
