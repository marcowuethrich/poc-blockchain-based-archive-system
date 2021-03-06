package com.archive.ingest

import com.archive.shared.model.dto.UploadDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Ingest controller
 * REST endpoint for uploading archives from producer
 */
@RestController
class IngestController(private val ingestService: IngestService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "ingest"
        const val API_SERVICE = "sip"
    }

    @PostMapping("/$API_V1/$API_ROOT/$API_SERVICE", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadSIP(
        @RequestPart(required = false) file: List<MultipartFile>,
        @RequestPart @Valid @NotNull @NotBlank meta: UploadDto
    ) = this.ingestService.uploadSIP(file, meta)
}
