package com.archive.datamanagement.controller

import com.archive.datamanagement.service.MetaDataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetaDataController(private val metaDataService: MetaDataService) {

    @GetMapping("/test")
    fun test() = this.metaDataService.test()
}
