package com.archive.ingest.service

import com.archive.ingest.client.DataManagementClient
import com.archive.ingest.dto.SIPDto
import org.springframework.stereotype.Service

@Service
class IngestService(private val dataManagementClient: DataManagementClient) {

    fun testCallOverFeign() = dataManagementClient.test()

    fun uploadIngest(sip: SIPDto) {

    }
}
