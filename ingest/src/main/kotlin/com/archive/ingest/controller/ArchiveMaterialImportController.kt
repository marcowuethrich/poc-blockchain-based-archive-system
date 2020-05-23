package com.archive.ingest.controller

import com.archive.ingest.dto.SIPDto
import com.archive.ingest.service.IngestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArchiveMaterialImportController(private val ingestService: IngestService) {

    @GetMapping("/test")
    fun test() = ingestService.testCallOverFeign()

    @PostMapping("/v1/upload")
    fun uploadSIP(sip: SIPDto) = this.ingestService.uploadIngest(sip)
}
