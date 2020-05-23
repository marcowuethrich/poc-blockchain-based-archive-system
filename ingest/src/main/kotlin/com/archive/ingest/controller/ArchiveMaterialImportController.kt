package com.archive.ingest.controller

import com.archive.ingest.dto.UploadDto
import com.archive.ingest.service.IngestService
import org.jetbrains.annotations.NotNull
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ArchiveMaterialImportController(private val ingestService: IngestService) {

    @GetMapping("/test")
    fun test() = ingestService.testCallOverFeign()

    @PostMapping("/v1/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSIP(@RequestPart("file") file: Set<MultipartFile>,
                  @NotNull @RequestPart("meta") dto: UploadDto) = this.ingestService.upload(file)
}
