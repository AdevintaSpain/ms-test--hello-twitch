package com.adevinta.mstesthellotwitch1.domain

import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class HelloPingPongTest {

  @Mock
  private lateinit var producer: HelloPongProducer

  @Test
  internal fun `should pong for each ping`() {
    val ping = HelloPing(123, "Test message!")

    val pingPong = HelloPingPong(producer)
    pingPong.consume(ping)

    val expectedPong = HelloPong(123, "Test message!", 13)
    verify(producer).produce(expectedPong)
  }
}
