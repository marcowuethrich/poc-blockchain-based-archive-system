package com.archive.datamanagement.controller

import com.archive.datamanagement.model.dbo.ArchiveObject
import com.archive.datamanagement.service.ArchiveService
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Archive Controller
 * API endpoints to store the meta data into the archive database
 */
@RestController
@RequestMapping("/datamanagement")
class ArchiveObjectController(private val archiveService: ArchiveService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "archives"
    }

    @GetMapping("/$API_V1/$API_ROOT")
    fun getAll() = this.archiveService.getAllArchiveObjects()

    @PostMapping("/$API_V1/$API_ROOT")
    fun save(@RequestBody entry: ArchiveObject) = this.archiveService.save(entry)

    @PutMapping("/$API_V1/$API_ROOT/{id}")
    fun update(@PathVariable id: UUID, @RequestBody entry: ArchiveObject) = this.archiveService.update(entry)

    @DeleteMapping("/$API_V1/$API_ROOT/{id}")
    fun delete(@PathVariable id: UUID) = this.archiveService.delete(id)
}
