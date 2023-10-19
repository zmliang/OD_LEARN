package com.zml.nohttp

import java.io.IOException
import java.util.concurrent.TimeUnit

class RealInterceptorChain(
    internal val call: RealCall,
    private val interceptors: List<Interceptor>,
    private val index: Int,
    internal val request: Request,
):Interceptor.Chain {

    var calls = 0

    internal fun copy(
        index: Int = this.index,
        request: Request = this.request,
        call:RealCall = this.call
    ) = RealInterceptorChain(call, interceptors, index,request)

    override fun request(): Request {
        return request
    }

    @Throws(IOException::class)
    override fun processed(request: Request): Response {
        if (index>interceptors.size){
            throw IndexOutOfBoundsException("")
        }
        calls++

        val next = copy(index = index+1,request = request)
        val interceptor = interceptors[index]

        val response = interceptor.intercept(next)

        return response
    }

    override fun connection(): Connection {

    }

    override fun call(): Call {
        return call
    }

    override fun connectTimeoutMillis(): Int {
        return call.client.connectTimeout()
    }

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {

    }

    override fun readTimeoutMillis(): Int {
        return call.client.readTimeout()
    }

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {

    }

    override fun writeTimeoutMillis(): Int {
        return call.client.writeTimeout()
    }

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {

    }
}