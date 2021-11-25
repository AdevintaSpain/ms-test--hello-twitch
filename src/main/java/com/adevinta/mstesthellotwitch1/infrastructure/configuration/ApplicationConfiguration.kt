package com.adevinta.mstesthellotwitch1.infrastructure.configuration

import com.adevinta.mstesthellotwitch1.application.SimpleHelloService
import com.adevinta.mstesthellotwitch1.domain.HelloPingConsumer
import com.adevinta.mstesthellotwitch1.domain.HelloPingPong
import com.adevinta.mstesthellotwitch1.domain.HelloPongProducer
import com.adevinta.mstesthellotwitch1.domain.HelloRepository
import com.adevinta.mstesthellotwitch1.infrastructure.repository.HelloJdbcRepository
import com.adevinta.mstesthellotwitch1.infrastructure.stream.HelloPingStreamConsumer
import com.adevinta.mstesthellotwitch1.infrastructure.stream.HelloPongStreamProducer
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ApplicationConfiguration {

  @Bean
  fun helloService(meterRegistry: MeterRegistry, helloRepository: HelloRepository) =
    SimpleHelloService(meterRegistry, helloRepository)

  @Bean
  fun helloRepository(datasource: DataSource) = HelloJdbcRepository(datasource)

  @Bean("hello-ping")
  fun helloPingStreamConsumer(helloPingConsumer: HelloPingConsumer) = HelloPingStreamConsumer(helloPingConsumer)

  @Bean("hello-pong")
  fun helloPongStreamProducer() = HelloPongStreamProducer()

  @Bean
  fun helloPingPong(producer: HelloPongProducer) = HelloPingPong(producer)
}
