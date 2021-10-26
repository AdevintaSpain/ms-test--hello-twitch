package com.adevinta.mstesthellotwitch1.infrastructure.testcases

import com.adevinta.mstesthellotwitch1.infrastructure.Application
import com.jayway.jsonassert.JsonAssert
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.awaitility.Durations.TEN_SECONDS
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.http.HttpStatus
import java.util.function.Consumer

@SpringBootTest(
  classes = [Application::class],
  properties = ["spring.profiles.active=integration-test,k8s"],
  webEnvironment = RANDOM_PORT
)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension::class)
abstract class LoggingTestCase {

  @Value("http://localhost:\${local.server.port}")
  private lateinit var localUrl: String

  @Test
  fun `should log field helloId in application log`(capturedOutput: CapturedOutput) {
    given()
      .baseUri(localUrl)
      .`when`()
      .get("/hello")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())

    await().atMost(TEN_SECONDS)
      .untilAsserted {
        assertThat(capturedOutput.out.lines())
          .filteredOn { it.startsWith("{") && it.contains("Hello twitch!") && it.endsWith("}") }
          .singleElement()
          .satisfies(
            Consumer {
              JsonAssert.with(it).assertThat("$.helloId", equalTo("1234"))
            }
          )
      }
  }

  @Test
  fun `should log header X-Hello in application log`(capturedOutput: CapturedOutput) {
    given()
      .baseUri(localUrl)
      .header("X-Hello", "abcd")
      .`when`()
      .get("/hello")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())

    await().atMost(TEN_SECONDS)
      .untilAsserted {
        assertThat(capturedOutput.out.lines())
          .filteredOn { it.startsWith("{") && it.contains("Hello twitch!") && it.endsWith("}") }
          .singleElement()
          .satisfies(
            Consumer {
              JsonAssert.with(it).assertThat("$.header-X-Hello", equalTo("abcd"))
            }
          )
      }
  }

  @Test
  fun `should log header X-Hello in access log`(capturedOutput: CapturedOutput) {
    given()
      .baseUri(localUrl)
      .header("X-Hello", "abcd")
      .`when`()
      .get("/hello")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())

    await().atMost(TEN_SECONDS)
      .untilAsserted {
        assertThat(capturedOutput.out.lines())
          .filteredOn { it.startsWith("{") && it.contains("/hello") && it.endsWith("}") }
          .singleElement()
          .satisfies(
            Consumer {
              JsonAssert.with(it).assertThat("$.header-X-Hello", equalTo("abcd"))
            }
          )
      }
  }
}
