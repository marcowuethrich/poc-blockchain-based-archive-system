package com.archive.ingest

import com.archive.shared.config.SharedConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import sawtooth.sdk.signing.Signer


@SpringBootApplication(
    exclude = [
        ErrorMvcAutoConfiguration::class,
        org.zalando.problem.spring.web.autoconfigure.security.SecurityConfiguration::class,
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class
    ]
)
@Import(value = [SharedConfig::class])
class IngestApplication(private val signer: Signer)

fun main(args: Array<String>) {
    runApplication<IngestApplication>(*args)
}
