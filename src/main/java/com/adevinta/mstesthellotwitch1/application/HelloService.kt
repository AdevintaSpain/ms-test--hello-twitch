package com.adevinta.mstesthellotwitch1.application

import com.adevinta.mstesthellotwitch1.domain.HelloRepository
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

interface HelloService {

  fun sayHello(): String
}

class SimpleHelloService(
  private val meterRegistry: MeterRegistry,
  private val helloRepository: HelloRepository
) : HelloService {

  private val logger: Logger = LoggerFactory.getLogger(HelloService::class.java)

  override fun sayHello(): String {
    MDC.putCloseable("helloId", "1234").use {
      logger.info("Hello twitch!")
    }

    meterRegistry.counter("hellos", listOf(Tag.of("tag1", "abc"))).increment()

    return helloRepository.getHelloMessage()
  }
}
