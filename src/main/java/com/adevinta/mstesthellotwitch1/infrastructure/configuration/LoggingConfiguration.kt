package com.adevinta.mstesthellotwitch1.infrastructure.configuration

import com.adevinta.mstesthellotwitch1.infrastructure.logging.HeadersLoggingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingConfiguration {

  @Bean
  fun headersLoggingFilter() = HeadersLoggingFilter()
}
