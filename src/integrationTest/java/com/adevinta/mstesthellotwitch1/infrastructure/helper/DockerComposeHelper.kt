package com.adevinta.mstesthellotwitch1.infrastructure.helper

import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import java.io.File

class DockerComposeHelper {

  private val POSTGRES = "postgres"
  private val POSTGRES_PORT = 5432

  private val KAFKA = "kafka"
  private val KAFKA_PORT = 9094

  private val ZOOKEEPER = "zookeeper"
  private val ZOOKEEPER_PORT = 2181

  private val container: DockerComposeContainer<*>

  init {
    container = DockerComposeContainer<Nothing>(File("docker-compose.yml"))
      .apply { withLocalCompose(true) }
      .apply {
        withExposedService(
          POSTGRES,
          POSTGRES_PORT,
          WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
            .apply { withStrategy(Wait.forListeningPort()) }
            .apply { withStrategy(Wait.forLogMessage(".*database system is ready to accept connections.*", 1)) }
        )
      }
      .apply {
        withExposedService(
          KAFKA,
          KAFKA_PORT,
          WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
            .apply { withStrategy(Wait.forListeningPort()) }
            .apply { withStrategy(Wait.forLogMessage(".*creating topics.*", 1)) }
        )
      }
      .apply {
        withExposedService(
          ZOOKEEPER,
          ZOOKEEPER_PORT,
          WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
            .apply { withStrategy(Wait.forListeningPort()) }
            .apply { withStrategy(Wait.forLogMessage(".*binding to port.*", 1)) }
        )
      }
  }

  fun start() {
    container.start()
    setSystemProperties()
  }

  private fun setSystemProperties() {
    System.setProperty("database.host", container.getServiceHost(POSTGRES, POSTGRES_PORT))
    System.setProperty("database.port", container.getServicePort(POSTGRES, POSTGRES_PORT).toString())
    System.setProperty(
      "spring.cloud.stream.kafka.binder.brokers",
      "${container.getServiceHost(KAFKA, KAFKA_PORT)}:${container.getServicePort(KAFKA, KAFKA_PORT)}"
    )
  }

  fun stop() {
    container.stop()
  }
}
