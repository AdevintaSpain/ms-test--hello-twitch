package com.adevinta.mstesthellotwitch1.infrastructure.stream

import com.adevinta.mstesthellotwitch1.domain.HelloPong
import com.adevinta.mstesthellotwitch1.domain.HelloPongProducer
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST
import java.util.function.Supplier

data class HelloPongDto(val pong: Int, val message: String, val len: Int)

class HelloPongStreamProducer : HelloPongProducer, Supplier<Flux<Message<HelloPongDto>>> {

  private val sink = Sinks.many().unicast()
    .onBackpressureBuffer<Message<HelloPongDto>>()

  override fun get(): Flux<Message<HelloPongDto>> = sink.asFlux()

  override fun produce(pong: HelloPong) {
    val message = MessageBuilder
      .withPayload(toDto(pong))
      .build()
    sink.emitNext(message, FAIL_FAST)
  }

  private fun toDto(pong: HelloPong): HelloPongDto = HelloPongDto(pong.pong, pong.message, pong.len)
}
