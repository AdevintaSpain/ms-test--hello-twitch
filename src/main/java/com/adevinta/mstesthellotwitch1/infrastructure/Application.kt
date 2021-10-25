package com.adevinta.mstesthellotwitch1.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(
  scanBasePackages = [
    "com.adevinta.mstesthellotwitch1.infrastructure.controller",
    "com.adevinta.mstesthellotwitch1.infrastructure.configuration"
  ]
)

@EnableFeignClients
class Application

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
