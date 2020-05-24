package com.archive.ingest.client

import com.archive.ingest.model.dbo.ArchiveObject
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(name = "data-management-client", url = "\${archive.data-management.url}")
interface DataManagementClient {

    companion object {
        const val BASE_URL: String = "/datamanagement/v1/archives"
    }

    @GetMapping(BASE_URL)
    fun getAll()

    @PostMapping(BASE_URL)
    fun save(@RequestBody entry: ArchiveObject)

    @PutMapping("$BASE_URL/{id}")
    fun update(@PathVariable id: UUID, @RequestBody entry: ArchiveObject)

    @DeleteMapping("$BASE_URL/{id}")
    fun delete(@PathVariable id: UUID)
}
