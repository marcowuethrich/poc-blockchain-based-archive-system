package com.archive.shared.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "sawtooth-client", url = "\${archive.blockchain.url}")
interface SawtoothClient {

    @PostMapping("/batches", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun postBatchList(@RequestBody payload: ByteArray): ResponseEntity<Any>

}
