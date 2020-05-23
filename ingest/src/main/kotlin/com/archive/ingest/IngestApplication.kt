package com.archive.ingest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class IngestApplication

fun main(args: Array<String>) {
	runApplication<IngestApplication>(*args)
}
