package com.archive.datamanagement

import com.archive.shared.model.dbo.ArchiveObjectDbo
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Archive Controller
 * API endpoints to store the meta data into the archive database
 */
@RestController
@RequestMapping("/datamanagement")
class DataManagementController(private val service: DataManagementService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "archives"
    }

    @GetMapping("/$API_V1/$API_ROOT")
    fun getAll(): Iterable<ArchiveObjectDbo> = this.service.getAllArchiveObjects()

    @PostMapping("/$API_V1/$API_ROOT")
    fun save(@RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo = this.service.save(entry)

    @PutMapping("/$API_V1/$API_ROOT/{id}")
    fun update(@PathVariable id: UUID, @RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo =
        this.service.update(entry)

    @PutMapping("/$API_V1/$API_ROOT/{id}/{ref}")
    fun updateBlockchainRef(@PathVariable id: UUID, @PathVariable ref: String) =
        this.service.updateBlockchainRef(id, ref)

    @DeleteMapping("/$API_V1/$API_ROOT/{id}")
    fun delete(@PathVariable id: UUID) = this.service.delete(id)

    @DeleteMapping("/$API_V1/$API_ROOT/clear")
    fun clear() = service.clear()
}
