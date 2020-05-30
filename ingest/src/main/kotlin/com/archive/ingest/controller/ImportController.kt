package com.archive.ingest.controller

import com.archive.ingest.service.IngestService
import com.archive.shared.model.dto.UploadDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/ingest")
class ImportController(private val ingestService: IngestService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "upload"
    }

    @PostMapping("/$API_V1/$API_ROOT", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSIP(
        @RequestPart(required = false) file: List<MultipartFile>,
        @RequestPart @Valid @NotNull @NotBlank meta: UploadDto
    ) = this.ingestService.upload(file, meta)
}
