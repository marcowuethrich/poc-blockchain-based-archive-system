package com.archive.ingest

import com.archive.shared.config.SharedConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [SharedConfig::class])
class IngestApplication

fun main(args: Array<String>) {
    runApplication<IngestApplication>(*args)
}
