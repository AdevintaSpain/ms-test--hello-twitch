package com.adevinta.mstesthellotwitch1.infrastructure.logging

import ch.qos.logback.access.pattern.AccessConverter
import ch.qos.logback.access.spi.IAccessEvent

class RealClientIpConverter : AccessConverter() {

  private val X_ORIGINAL_FORWARDED_FOR_HEADER = "X-Original-Forwarded-For"

  override fun convert(event: IAccessEvent): String? {
    val xOriginalForwardedFor = event.getRequestHeader(X_ORIGINAL_FORWARDED_FOR_HEADER)
    return if (isNullOrEmpty(xOriginalForwardedFor))
      event.remoteAddr
    else
      xOriginalForwardedFor.split(",").toTypedArray()[0]
  }

  private fun isNullOrEmpty(header: String?): Boolean {
    return null == header || header.isEmpty() || header == "-"
  }
}
