package com.archive.datamanagement

import com.archive.shared.config.SharedConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [SharedConfig::class])
class DataManagementApplication

fun main(args: Array<String>) {
    runApplication<DataManagementApplication>(*args)
}
