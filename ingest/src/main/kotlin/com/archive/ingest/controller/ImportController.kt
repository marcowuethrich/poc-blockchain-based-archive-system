package com.archive.ingest.controller

import com.archive.ingest.service.IngestService
import com.archive.shared.model.dto.UploadDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ImportController(private val ingestService: IngestService) {

    @PostMapping("/v1/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSIP(
        @RequestPart("file") file: List<MultipartFile>,
        @RequestPart("meta") dto: UploadDto
    ) = this.ingestService.upload(file, dto)
}
