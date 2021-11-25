package com.adevinta.mstesthellotwitch1.infrastructure.testcases

import com.adevinta.mstesthellotwitch1.infrastructure.Application
import com.adevinta.mstesthellotwitch1.infrastructure.helper.KafkaConsumerHelper
import com.adevinta.mstesthellotwitch1.infrastructure.helper.KafkaProducerHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import java.time.Duration
import java.util.function.Consumer

@SpringBootTest(
  classes = [Application::class],
  properties = ["spring.profiles.active=integration-test"],
  webEnvironment = NONE
)
abstract class HelloKafkaTestCase {

  @Value("\${spring.cloud.stream.kafka.binder.brokers}")
  private lateinit var kafkaBrokers: String

  @Value("\${spring.cloud.stream.bindings.hello-ping-in-0.destination}")
  private lateinit var inputTopic: String

  @Value("\${spring.cloud.stream.bindings.hello-pong-out-0.destination}")
  private lateinit var outputTopic: String

  private lateinit var kafkaConsumerHelper: KafkaConsumerHelper
  private lateinit var kafkaProducerHelper: KafkaProducerHelper

  @BeforeEach
  internal fun setUp() {
    kafkaConsumerHelper = KafkaConsumerHelper(kafkaBrokers, outputTopic)
    kafkaProducerHelper = KafkaProducerHelper(kafkaBrokers)
  }

  @Test
  internal fun `should produce pong when ping is received`() {
    kafkaProducerHelper.send(inputTopic, """{"ping":123,"message":"hi!"}""")

    val records = kafkaConsumerHelper.consumeAtLeast(1, Duration.ofSeconds(5))

    assertThat(records).singleElement().satisfies(Consumer {
      assertThat(it.value()).isEqualTo("""{"pong":123,"message":"hi!","len":3}""")
    })
  }
}
