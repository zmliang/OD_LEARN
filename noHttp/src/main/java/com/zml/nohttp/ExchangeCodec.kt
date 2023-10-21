package com.zml.nohttp

import okio.Sink
import okio.Source
import java.io.IOException

interface ExchangeCodec {


    val connection: RealConnection

    @Throws(IOException::class)
    fun createRequestBody(request: Request, contentLength: Long): Sink

    @Throws(IOException::class)
    fun writeRequestHeaders(request: Request)

    @Throws(IOException::class)
    fun flushRequest()

    @Throws(IOException::class)
    fun finishRequest()

    @Throws(IOException::class)
    fun readResponseHeaders(expectContinue: Boolean): Response.Builder?

    @Throws(IOException::class)
    fun reportedContentLength(response: Response): Long

    @Throws(IOException::class)
    fun openResponseBodySource(response: Response): Source

    @Throws(IOException::class)
    fun trailers(): Headers

    fun cancel()

    companion object {

        const val DISCARD_STREAM_TIMEOUT_MILLIS = 100
    }

}