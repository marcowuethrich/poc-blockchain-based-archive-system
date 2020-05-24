package com.archive.ingest.controller

import com.archive.ingest.model.dto.UploadDto
import com.archive.ingest.service.IngestService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ArchiveMaterialImportController(private val ingestService: IngestService) {

    @PostMapping("/v1/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSIP(@RequestPart("file") file: Set<MultipartFile>,
                  @RequestPart("meta") dto: UploadDto) = this.ingestService.upload(file, dto)
}
