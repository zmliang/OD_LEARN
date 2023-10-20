package com.zml.nohttp

import android.util.Log
import java.io.IOException
import java.util.concurrent.TimeUnit

class RealInterceptorChain(
    internal val call: RealCall,
    private val interceptors: List<Interceptor>,
    private val index: Int,
    internal val request: Request,
    private val readTimeout:Int,
    private val writeTimeout:Int,
    private val connectTimeout:Int
):Interceptor.Chain {

    var calls = 0

    internal fun copy(
        index: Int = this.index,
        request: Request = this.request,
        call:RealCall = this.call,
        readTimeout: Int = this.readTimeout,
        writeTimeout: Int = this.writeTimeout,
        connectTimeout: Int = this.connectTimeout
    ) = RealInterceptorChain(call, interceptors, index,request,readTimeout,writeTimeout,connectTimeout)

    override fun request(): Request {
        return request
    }

    @Throws(IOException::class)
    override fun processed(request: Request): Response {
        Log.i("zml","start realChain proceed")
        if (index>interceptors.size){
            throw IndexOutOfBoundsException("")
        }
        calls++

        val next = copy(index = index+1,request = request)
        val interceptor = interceptors[index]

        val response = interceptor.intercept(next)

        Log.i("zml","next realChain proceed")
        return response
    }

    override fun connection(): Connection {
        return RealConnection()
    }

    override fun call(): Call {
        return call
    }

    override fun connectTimeoutMillis(): Int {
        return call.client.connectTimeout()
    }

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        return copy(connectTimeout = timeout)
    }

    override fun readTimeoutMillis(): Int {
        return call.client.readTimeout()
    }

    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        return copy(readTimeout = timeout)
    }

    override fun writeTimeoutMillis(): Int {
        return call.client.writeTimeout()
    }

    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        return copy(writeTimeout = timeout)
    }
}