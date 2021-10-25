package com.adevinta.mstesthellotwitch1.infrastructure.logging

import ch.qos.logback.access.spi.IAccessEvent
import com.fasterxml.jackson.core.JsonGenerator
import net.logstash.logback.composite.AbstractJsonProvider

class HeadersLoggingJsonProvider: AbstractJsonProvider<IAccessEvent>() {

  override fun writeTo(generator: JsonGenerator?, event: IAccessEvent?) {
    for (header in HEADERS_TO_LOG) {
      generator!!.writeStringField(
        "header-${header}",
        event!!.getRequestHeader(header) ?: "-"
      )
    }
  }
}
