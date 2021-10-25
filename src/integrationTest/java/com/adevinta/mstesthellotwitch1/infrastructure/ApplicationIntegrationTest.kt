package com.adevinta.mstesthellotwitch1.infrastructure

import com.adevinta.mstesthellotwitch1.infrastructure.helper.DockerComposeHelper
import com.adevinta.mstesthellotwitch1.infrastructure.testcases.ApplicationTestCase
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested

class ApplicationIntegrationTest {
  companion object {

    private val dockerCompose = DockerComposeHelper()

    @BeforeAll
    @JvmStatic
    fun dockerComposeUp() {
      dockerCompose.start()
    }

    @AfterAll
    @JvmStatic
    fun dockerComposeDown() {
      dockerCompose.stop()
    }
  }

  @Nested
  inner class Application : ApplicationTestCase()
}
