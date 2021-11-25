package com.adevinta.mstesthellotwitch1.domain

data class HelloPing(val ping: Int, val message: String)
interface HelloPingConsumer {
  fun consume(ping: HelloPing)
}

data class HelloPong(val pong: Int, val message: String, val len: Int)
interface HelloPongProducer {
  fun produce(pong: HelloPong)
}

class HelloPingPong(private val producer: HelloPongProducer) : HelloPingConsumer {

  private fun toPong(ping: HelloPing) = HelloPong(ping.ping, ping.message, ping.message.length)

  override fun consume(ping: HelloPing) {
    producer.produce(toPong(ping))
  }
}
