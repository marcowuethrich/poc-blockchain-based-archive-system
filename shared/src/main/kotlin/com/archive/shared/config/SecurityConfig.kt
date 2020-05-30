package com.archive.shared.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
@Import(
    SharedConfig::class,
    SecurityProblemSupport::class
)
class SecurityConfig(
    private val problemSupport: SecurityProblemSupport
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http

            // enable problem support for security exceptions
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()

            // request auth
            .authorizeRequests()
            .antMatchers(
                // api
                "/ingest/**",
                "/datamanagement/**",
                "/archival-storage/**",
                "/access/**",

                // api doc
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/api/doc/**"
            ).permitAll()

            // default
            .antMatchers(
                "/**"
            ).authenticated()

            .and()

            .cors()
            .and()

            // disable csrf
            .csrf()
            .disable()

            // disable sessions
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            // disable form login
            .formLogin()
            .disable()
        // @formatter:on
    }
}
