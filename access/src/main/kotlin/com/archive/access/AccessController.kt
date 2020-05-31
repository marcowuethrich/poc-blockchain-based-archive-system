package com.archive.access

import com.archive.shared.model.dto.AIPDto
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Access Controller
 * API endpoints to consume the archival objects
 */
@RestController
class AccessController(private val service: AccessService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "access"
        const val API_SERVICE = "aips"
    }

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}/content", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getContent(@PathVariable id: UUID): ResponseEntity<Resource> = service.getContent(id)

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}/meta")
    fun getMeta(@PathVariable id: UUID): AIPDto = service.getMeta(id)
}
