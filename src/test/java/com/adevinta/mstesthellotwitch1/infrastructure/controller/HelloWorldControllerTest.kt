package com.adevinta.mstesthellotwitch1.infrastructure.controller

import com.adevinta.mstesthellotwitch1.infrastructure.Application
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(HelloWorldController::class)
@ContextConfiguration(classes = [Application::class])
class HelloWorldControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  fun shouldSayHello() {
    mvc.perform(get("/hello"))
      .andExpect(status().isOk)
      .andExpect(content().string(equalTo("Hello twitch!")))
  }
}
