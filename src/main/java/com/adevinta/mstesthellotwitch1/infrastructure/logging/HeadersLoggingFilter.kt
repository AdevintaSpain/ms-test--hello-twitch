package com.adevinta.mstesthellotwitch1.infrastructure.logging

import org.slf4j.MDC
import java.io.Closeable
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.streams.toList

class HeadersLoggingFilter : Filter {

  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    if (request is HttpServletRequest) {
      doFilterHttp(request, response!!, chain!!)
    } else {
      chain?.doFilter(request, response)
    }
  }

  private fun doFilterHttp(request: HttpServletRequest, response: ServletResponse, chain: FilterChain) {
    mdcCloseableListFrom(request).use {
      chain.doFilter(request, response)
    }
  }

  private fun mdcCloseableListFrom(request: HttpServletRequest) =
    HEADERS_TO_LOG.stream()
      .map { MDC.putCloseable(it, request.getHeader(it) ?: "-") }
      .toList()
      .let { CloseableList(it) }
}

private class CloseableList(private val closeables: List<Closeable>) : Closeable {

  override fun close() {
    closeables.forEach { it.close() }
  }
}
