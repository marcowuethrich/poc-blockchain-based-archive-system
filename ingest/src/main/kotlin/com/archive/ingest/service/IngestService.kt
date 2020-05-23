package com.archive.ingest.service

import com.archive.ingest.client.DataManagementClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class IngestService(private val dataManagementClient: DataManagementClient) {

    fun testCallOverFeign() = dataManagementClient.test()

    fun upload(files: Set<MultipartFile>) {

    }

}
