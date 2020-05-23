package com.archive.ingest.service

import com.archive.ingest.client.DataManagementClient
import org.springframework.stereotype.Service

@Service
class IngestService(private val dataManagementClient: DataManagementClient) {

    fun testCallOverFeign() = dataManagementClient.test()
}
