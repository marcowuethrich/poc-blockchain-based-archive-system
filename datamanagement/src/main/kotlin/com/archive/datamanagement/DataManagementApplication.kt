package com.archive.datamanagement

import com.archive.shared.config.JpaConfig
import com.archive.shared.config.SharedConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(
    exclude = [
        ErrorMvcAutoConfiguration::class,
        org.zalando.problem.spring.web.autoconfigure.security.SecurityConfiguration::class
    ]
)
@Import(
    value = [
        SharedConfig::class,
        JpaConfig::class
    ]
)
class DataManagementApplication

fun main(args: Array<String>) {
    runApplication<DataManagementApplication>(*args)
}
