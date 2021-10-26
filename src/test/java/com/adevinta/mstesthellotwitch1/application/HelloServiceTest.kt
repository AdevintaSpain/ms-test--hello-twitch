package com.adevinta.mstesthellotwitch1.application

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HelloServiceTest {

  lateinit var meterRegistry: MeterRegistry
  lateinit var helloService: HelloService

  @BeforeEach
  fun beforeEach() {
    meterRegistry = SimpleMeterRegistry()
    helloService = SimpleHelloService(meterRegistry)
  }

  @Test
  fun `should increment counter`() {
    helloService.sayHello()

    assertThat(
      meterRegistry
        .counter("hellos", "tag1", "abc")
        .count()
    ).isEqualTo(1.0)
  }
}
