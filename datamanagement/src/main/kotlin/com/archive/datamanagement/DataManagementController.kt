package com.archive.datamanagement

import com.archive.shared.model.dbo.ArchiveObjectDbo
import com.archive.shared.model.dto.AIPDto
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Archive Controller
 * API endpoints to store the meta data into the archive database
 */
@RestController
class DataManagementController(private val service: DataManagementService) {

    companion object {
        const val API_V1 = "v1"
        const val API_ROOT = "datamanagement"
        const val API_SERVICE = "archives"
    }

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE")
    fun getAll(): Iterable<ArchiveObjectDbo> = this.service.getAllArchiveObjects()

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE/{aipId}/aip")
    fun getAIP(@PathVariable aipId: UUID): AIPDto = this.service.getAIP(aipId)

    @PostMapping("/$API_V1/$API_ROOT/$API_SERVICE")
    fun save(@RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo = this.service.save(entry)

    @PutMapping("/$API_V1/$API_ROOT/$API_SERVICE/{aipId}")
    fun update(@PathVariable aipId: UUID, @RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo =
        this.service.update(aipId, entry)

    @PutMapping("/$API_V1/$API_ROOT/$API_SERVICE/{aipId}/{address}")
    fun updateBlockchainAddress(@PathVariable aipId: UUID, @PathVariable address: String) =
        this.service.updateBlockchainAddress(aipId, address)

    @GetMapping("/$API_V1/$API_ROOT/$API_SERVICE/{aipId}/blockchain-address")
    fun getBlockchainAddress(@PathVariable aipId: UUID): String = this.service.getBlockchainAddress(aipId)

    @DeleteMapping("/$API_V1/$API_ROOT/$API_SERVICE/{aipId}")
    fun delete(@PathVariable aipId: UUID) = this.service.delete(aipId)

    @DeleteMapping("/$API_V1/$API_ROOT/$API_SERVICE/clear")
    fun clear() = service.clear()
}
