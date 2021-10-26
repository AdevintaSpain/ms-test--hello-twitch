package com.adevinta.mstesthellotwitch1.infrastructure.controller

import com.adevinta.mstesthellotwitch1.application.HelloService
import com.adevinta.mstesthellotwitch1.infrastructure.Application
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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

  @MockBean
  private lateinit var helloService: HelloService

  @Test
  fun shouldSayHello() {
    doReturn("Hello potato!").whenever(helloService).sayHello()

    mvc.perform(get("/hello"))
      .andExpect(status().isOk)
      .andExpect(content().string(equalTo("Hello potato!")))
  }
}
