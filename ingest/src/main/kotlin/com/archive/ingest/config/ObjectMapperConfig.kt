package com.archive.ingest.config

import com.archive.shared.config.ObjectMapperConfigurer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.zalando.problem.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule

@Configuration
class ObjectMapperConfig {

  @Bean
  @Primary
  fun objectMapper(): ObjectMapper =
    ObjectMapper()
      .let { ObjectMapperConfigurer.configure(it) }
      .registerModule(ProblemModule())
      .registerModule(ConstraintViolationProblemModule())
}
