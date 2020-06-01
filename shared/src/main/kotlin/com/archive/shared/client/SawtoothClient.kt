package com.archive.shared.client

import com.archive.shared.model.dto.SawtoothStateResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * Archival storage client
 * Feign client for access to the hyperledger sawtooth
 */
@FeignClient(name = "sawtooth-client", url = "\${archive.blockchain.url}")
interface SawtoothClient {

    @PostMapping("/batches", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun postBatchList(@RequestBody payload: ByteArray): ResponseEntity<Any>

    @GetMapping("/state/{address}")
    fun getState(@PathVariable address: String): SawtoothStateResponseDto

}
