package com.archive.shared.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@FeignClient(name = "archival-storage-client", url = "\${archive.archival-storage.url}")
interface ArchivalStorageClient {

    companion object {
        const val BASE_URL: String = "/v1/archival-storage/storage"
    }

    @GetMapping("$BASE_URL/{contentId}")
    fun get(@PathVariable contentId: UUID): ResponseEntity<Resource>

    @PostMapping("$BASE_URL/{contentId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun add(@PathVariable contentId: UUID, @RequestPart content: MultipartFile)

    @PutMapping("$BASE_URL/{contentId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun replace(@PathVariable contentId: UUID, @RequestPart content: MultipartFile)

    @DeleteMapping("$BASE_URL/{contentId}")
    fun delete(@PathVariable contentId: UUID)
}
