package com.archive.ingest.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "data-management-client", url = "\${archive.data-management.url}")
interface DataManagementClient {

    @GetMapping("/test")
    fun test(): String
}
