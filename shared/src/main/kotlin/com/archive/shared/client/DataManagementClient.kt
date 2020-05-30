package com.archive.shared.client

import com.archive.shared.model.dbo.ArchiveObjectDbo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(name = "data-management-client", url = "\${archive.data-management.url}")
interface DataManagementClient {

    companion object {
        const val BASE_URL: String = "/datamanagement/v1/archives"
    }

    @GetMapping(BASE_URL)
    fun getAll(): Iterable<ArchiveObjectDbo>

    @PostMapping(BASE_URL)
    fun save(@RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo

    @PutMapping("$BASE_URL/{id}")
    fun update(@PathVariable id: UUID, @RequestBody entry: ArchiveObjectDbo): ArchiveObjectDbo

    @PutMapping("$BASE_URL/{id}/{ref}")
    fun updateBlockchainRef(@PathVariable id: UUID, @PathVariable ref: String)

    @DeleteMapping("$BASE_URL/{id}")
    fun delete(@PathVariable id: UUID)
}
