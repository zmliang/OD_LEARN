package com.zml.nohttp

import okio.Sink
import okio.Source

class Http1Codec(override val connection: RealConnection) :ExchangeCodec {
    override fun createRequestBody(request: Request, contentLength: Long): Sink {
        TODO("Not yet implemented")
    }

    override fun writeRequestHeaders(request: Request) {
        TODO("Not yet implemented")
    }

    override fun flushRequest() {
        TODO("Not yet implemented")
    }

    override fun finishRequest() {
        TODO("Not yet implemented")
    }

    override fun readResponseHeaders(expectContinue: Boolean): Response.Builder? {
        TODO("Not yet implemented")
    }

    override fun reportedContentLength(response: Response): Long {
        TODO("Not yet implemented")
    }

    override fun openResponseBodySource(response: Response): Source {
        TODO("Not yet implemented")
    }

    override fun trailers(): Headers {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}