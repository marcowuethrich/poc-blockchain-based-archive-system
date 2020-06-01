package com.archive.shared.client

import com.archive.shared.model.dbo.ArchiveObjectDbo
import com.archive.shared.model.dto.AIPDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(name = "data-management-client", url = "\${archive.data-management.url}")
interface DataManagementClient {

    companion object {
        const val BASE_URL: String = "/v1/datamanagement/archives"
    }

    @GetMapping(BASE_URL)
    fun getAll(): Iterable<ArchiveObjectDbo>

    @PostMapping(BASE_URL)
    fun save(@RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo

    @PutMapping("$BASE_URL/{aipId}")
    fun update(@PathVariable aipId: UUID, @RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo

    @PutMapping("$BASE_URL/{aipId}/{address}")
    fun updateBlockchainAddress(@PathVariable aipId: UUID, @PathVariable address: String)

    @DeleteMapping("$BASE_URL/{aipId}")
    fun delete(@PathVariable aipId: UUID)

    @GetMapping("$BASE_URL/{aipId}/aip")
    fun getAIP(@PathVariable aipId: UUID): AIPDto

    @GetMapping("$BASE_URL/{aipId}/blockchain-address")
    fun getBlockchainAddress(@PathVariable aipId: UUID): String
}
