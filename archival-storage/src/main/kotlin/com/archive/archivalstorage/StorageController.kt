package com.archive.archivalstorage

import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Storage Controller
 * API endpoints to store content
 */
@RestController
class StorageController(private val service: StorageService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "archival-storage"
        const val API_SERVICE = "storage"
    }

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<Resource> = service.getContent(id)

    @PostMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun add(@PathVariable id: UUID, @RequestPart content: MultipartFile) = service.addContent(id, content)

    @PutMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun replace(@PathVariable id: UUID, @RequestPart content: MultipartFile) = service.replaceContent(id, content)

    @DeleteMapping("/$API_V1/$API_ROOT/$API_SERVICE/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteContent(id)
}