package com.adevinta.mstesthellotwitch1.infrastructure.configuration

import com.adevinta.mstesthellotwitch1.application.HelloService
import com.adevinta.mstesthellotwitch1.application.SimpleHelloService
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

  @Bean
  fun helloService(meterRegistry: MeterRegistry): HelloService = SimpleHelloService(meterRegistry)
}
