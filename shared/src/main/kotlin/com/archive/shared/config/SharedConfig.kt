package com.archive.shared.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    "com.archive.shared"
)
@EnableFeignClients(basePackages = ["com.archive.shared.client"])
class SharedConfig
