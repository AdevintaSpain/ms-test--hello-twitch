package com.adevinta.mstesthellotwitch1.infrastructure.stream

import com.adevinta.mstesthellotwitch1.domain.HelloPing
import com.adevinta.mstesthellotwitch1.domain.HelloPingConsumer
import org.springframework.messaging.Message
import java.util.function.Consumer

data class HelloPingDto(val ping: Int, val message: String)

class HelloPingStreamConsumer(private val consumer: HelloPingConsumer) : Consumer<Message<HelloPingDto>> {

  override fun accept(message: Message<HelloPingDto>) = consumer.consume(fromDto(message.payload))

  private fun fromDto(pingDto: HelloPingDto): HelloPing = HelloPing(pingDto.ping, pingDto.message)
}
