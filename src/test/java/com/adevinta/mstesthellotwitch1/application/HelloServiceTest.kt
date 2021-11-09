package com.adevinta.mstesthellotwitch1.application

import com.adevinta.mstesthellotwitch1.domain.HelloRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class HelloServiceTest {

  lateinit var meterRegistry: MeterRegistry
  lateinit var helloService: HelloService

  @Mock
  lateinit var helloRepository: HelloRepository

  @BeforeEach
  fun beforeEach() {
    meterRegistry = SimpleMeterRegistry()
    helloService = SimpleHelloService(meterRegistry, helloRepository)

    doReturn("Hello test twitch!").whenever(helloRepository).getHelloMessage()
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

  @Test
  fun `should get message from repository`() {
    assertThat(helloService.sayHello()).isEqualTo("Hello test twitch!")
  }
}
