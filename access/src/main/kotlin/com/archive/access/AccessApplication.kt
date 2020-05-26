package com.archive.access

import com.archive.shared.config.SharedConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [SharedConfig::class])
class AccessApplication

fun main(args: Array<String>) {
	runApplication<AccessApplication>(*args)
}
