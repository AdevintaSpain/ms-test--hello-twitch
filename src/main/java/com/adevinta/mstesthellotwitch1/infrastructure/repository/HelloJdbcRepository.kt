package com.adevinta.mstesthellotwitch1.infrastructure.repository

import com.adevinta.mstesthellotwitch1.domain.HelloRepository
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class HelloJdbcRepository(private val dataSource: DataSource) : HelloRepository {
  override fun getHelloMessage(): String {
    val jdbcTemplate = JdbcTemplate(dataSource)
    return jdbcTemplate.queryForList(
      "SELECT Message FROM HelloMessages limit 1",
      String::class.java
    ).singleOrNull() ?: "Hello by default!"
  }
}
