package com.archive.ingest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IngestApplication

fun main(args: Array<String>) {
	runApplication<IngestApplication>(*args)
}
