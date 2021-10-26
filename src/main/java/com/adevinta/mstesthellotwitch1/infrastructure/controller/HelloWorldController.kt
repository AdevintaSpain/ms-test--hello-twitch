package com.adevinta.mstesthellotwitch1.infrastructure.controller

import com.adevinta.mstesthellotwitch1.application.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloWorldController(private val helloService: HelloService) {

  @GetMapping
  fun sayHello() = helloService.sayHello()
}
