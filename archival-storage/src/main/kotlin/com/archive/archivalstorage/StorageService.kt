package com.archive.archivalstorage

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Storage Service
 * Implements the logic of the storage modulê
 */
@Service
class StorageService(
    @Value("\${archive.archival-storage.store-path}") private val rootPath: String
) {

    fun getContent(id: UUID): ResponseEntity<Resource> {
        val file = File("$rootPath/$id")
        if (file.exists()) {
            return ResponseEntity.ok()
                .headers(HttpHeaders.EMPTY)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(ByteArrayResource(file.readBytes()))
        } else {
            throw FileNotFoundException()
        }
    }


    fun addContent(id: UUID, content: MultipartFile) {
        val file = File("$rootPath/$id")
        if (file.exists()) {
            throw FileAlreadyExistsException(file)
        } else {
            content.transferTo(file)
        }
    }

    fun replaceContent(id: UUID, content: MultipartFile) {
        val file = File("$rootPath/$id")
        if (!file.exists()) {
            addContent(id, content)
        } else {
            content.transferTo(file)
        }
    }


    fun deleteContent(id: UUID) {
        val file = File("$rootPath/$id")
        if (file.exists()) {
            file.delete()
        }
    }

}
