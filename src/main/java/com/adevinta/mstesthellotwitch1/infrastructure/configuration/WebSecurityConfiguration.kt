package com.adevinta.mstesthellotwitch1.infrastructure.configuration

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.boot.actuate.metrics.MetricsEndpoint
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@Suppress("EmptyClassBlock")
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity?) {
    http!!
      .authorizeRequests()
      .requestMatchers(EndpointRequest.to(MetricsEndpoint::class.java)).permitAll()
      .requestMatchers(EndpointRequest.to(HealthEndpoint::class.java)).permitAll()
      .requestMatchers(EndpointRequest.to(InfoEndpoint::class.java)).permitAll()
      .requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated()
      .anyRequest().permitAll()
      .and().httpBasic()  }
}
