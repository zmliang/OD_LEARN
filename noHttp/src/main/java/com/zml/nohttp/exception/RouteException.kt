package com.zml.nohttp.exception

import java.io.IOException

class RouteException internal constructor(val firstConnectException: IOException) :
    RuntimeException(firstConnectException) {
    var lastConnectException: IOException = firstConnectException
        private set

    fun addConnectException(e: IOException) {
        firstConnectException.addSuppressed(e)
        lastConnectException = e
    }
}