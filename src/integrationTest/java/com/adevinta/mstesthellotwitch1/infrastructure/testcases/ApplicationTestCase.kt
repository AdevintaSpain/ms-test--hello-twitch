package com.adevinta.mstesthellotwitch1.infrastructure.testcases

import com.adevinta.mstesthellotwitch1.infrastructure.Application
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.availability.AvailabilityChangeEvent.publish
import org.springframework.boot.availability.LivenessState
import org.springframework.boot.availability.ReadinessState
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(
  classes = [Application::class],
  properties = ["spring.profiles.active=integration-test"]
)
@AutoConfigureMockMvc
abstract class ApplicationTestCase {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var eventPublisher: ApplicationEventPublisher

  @BeforeEach
  fun setUp() {
    mockMvc(mvc)
  }

  @AfterEach
  fun tearDown() {
    publish(eventPublisher, this, LivenessState.CORRECT)
    publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC)
  }

  @Test
  fun should_be_healthy() {
    given()
      .`when`()
      .get("/health")
      .then()
      .assertThat(status().isOk)
      .body("status", equalTo("UP"))
  }

  @Test
  fun should_say_hello() {
    given()
      .`when`()
      .get("/hello")
      .then()
      .assertThat(status().isOk)
  }

  @Test
  fun should_expose_info() {
    given()
      .`when`()
      .get("/info")
      .then()
      .assertThat(status().isOk)
  }

  @Test
  fun should_expose_swagger() {
    given()
      .`when`()
      .get("/schema")
      .then()
      .assertThat(status().isOk)
      .body("paths.'/hello'.get.summary", equalTo("sayHello"))
  }

  @Test
  fun should_not_expose_shutdown_endpoint() {
    given()
      .`when`()
      .get("/shutdown")
      .then()
      .assertThat(status().is4xxClientError)
  }

  @Test
  fun should_be_alive() {
    given()
      .`when`()
      .get("/health/liveness")
      .then()
      .assertThat(status().isOk)
      .body("status", equalTo("UP"))
  }

  @Test
  fun should_not_be_alive() {
    publish(eventPublisher, this, LivenessState.BROKEN)
    given()
      .`when`()
      .get("/health/liveness")
      .then()
      .body("status", equalTo("DOWN"))
      .assertThat(status().isServiceUnavailable)
  }

  @Test
  fun should_be_ready() {
    given()
      .`when`()
      .get("/health/readiness")
      .then()
      .assertThat(status().isOk)
      .body("status", equalTo("UP"))
  }

  @Test
  fun should_not_be_ready() {
    publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC)
    given()
      .`when`()
      .get("/health/readiness")
      .then()
      .body("status", equalTo("OUT_OF_SERVICE"))
      .assertThat(status().isServiceUnavailable)
  }
}
