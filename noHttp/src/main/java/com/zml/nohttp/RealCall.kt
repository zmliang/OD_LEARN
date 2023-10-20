package com.zml.nohttp

import com.zml.nohttp.interceptors.BridgeInterceptor
import com.zml.nohttp.interceptors.CallServerInterceptor
import java.util.concurrent.atomic.AtomicBoolean

class RealCall(
    val client: NoHttpClient,
    val originRequest: Request
):Call {

    private val executed = AtomicBoolean()
    override fun execute(): Response {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback) {
        check(executed.compareAndSet(false, true)) { "Already Executed" }

        client.dispatcher().enqueue(AsyncCall(callback))
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled() {
        TODO("Not yet implemented")
    }

    override fun isExecuted() {
        TODO("Not yet implemented")
    }

    fun getResponseWithInterceptorChain():Response{
        val interceptors = mutableListOf<Interceptor>()
        interceptors+=client.interceptors()
        interceptors+=BridgeInterceptor()
        interceptors+= CallServerInterceptor()

        val chain = RealInterceptorChain(this,interceptors,0,originRequest,client.readTimeout(),client.readTimeout(),client.connectTimeout())

        val response = chain.processed(originRequest)

        return response
    }

    inner class AsyncCall(
        private val callback:Callback
    ){


        val host: String
            get() = originRequest.url.host

        val request: Request
            get() = originRequest

        val call: RealCall
            get() = this@RealCall

    }
}