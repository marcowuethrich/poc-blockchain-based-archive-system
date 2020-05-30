package com.archive.shared.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.zalando.problem.ProblemModule
import java.time.ZoneId
import java.util.*

object ObjectMapperConfigurer {

    fun configure(om: ObjectMapper): ObjectMapper =
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC").normalized()))
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .registerModule(KotlinModule())
            .registerModule(ParameterNamesModule())
            .registerModule(ProblemModule())
}
