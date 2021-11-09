package com.adevinta.mstesthellotwitch1.infrastructure.testcases

import com.adevinta.mstesthellotwitch1.domain.HelloRepository
import com.adevinta.mstesthellotwitch1.infrastructure.Application

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(
  classes = [Application::class],
  properties = ["spring.profiles.active=integration-test"],
  webEnvironment = NONE
)
abstract class HelloRepositoryTestCase {

  @Autowired
  lateinit var helloRepository: HelloRepository

  @Test
  @Sql(
    statements = [
      "DELETE FROM HelloMessages",
      "INSERT INTO HelloMessages (Message) VALUES ('Hello from the test!')"
    ]
  )
  fun `should get message from repository`() {
    assertThat(helloRepository.getHelloMessage())
      .isEqualTo("Hello from the test!")
  }

  @Test
  @Sql(
    statements = [
      "DELETE FROM HelloMessages"
    ]
  )
  internal fun `should return default message when message not found`() {
    assertThat(helloRepository.getHelloMessage())
      .isEqualTo("Hello by default!")
  }
}
