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
        client.dispatcher().enqueue(this)
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
        val interceptors = mutableListOf<Interceptor>();
        interceptors+=client.interceptors()
        interceptors+=BridgeInterceptor()
        interceptors+= CallServerInterceptor()

        val chain = RealInterceptorChain(this,interceptors,0,originRequest)

        val response = chain.processed(originRequest)

        return response
    }
}