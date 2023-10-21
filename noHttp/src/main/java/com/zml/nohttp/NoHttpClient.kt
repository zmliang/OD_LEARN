package com.zml.nohttp

import java.util.concurrent.TimeUnit

class NoHttpClient internal constructor(builder:Builder):INoHttp,Call.Factory{

    private val dispatcher = Dispatcher()
    private val connectTimeout = builder.connectTimeout
    private val readTimeout = builder.readTimeout
    private val writeTimeout = builder.writeTimeout
    private val interceptors = builder.interceptors

    @get:JvmName("retryOnConnectionFailure") val retryOnConnectionFailure: Boolean =
        builder.retryOnConnectionFailure

    @get:JvmName("pingIntervalMillis") val pingIntervalMillis: Int = builder.pingInterval

    fun dispatcher() = dispatcher
    fun connectTimeout() = connectTimeout
    fun readTimeout() = readTimeout
    fun writeTimeout() = writeTimeout
    fun interceptors() = interceptors

    class Builder constructor(){
        internal var connectTimeout = 10_000
        internal var readTimeout = 10_000
        internal var writeTimeout = 10_000
        internal val interceptors: MutableList<Interceptor> = mutableListOf()
        internal var retryOnConnectionFailure = false
        internal var pingInterval:Int = 0

        fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean) = apply {
            this.retryOnConnectionFailure = retryOnConnectionFailure
        }

        fun pingInterval(interval: Long, unit: TimeUnit) = apply {
            pingInterval = checkDuration("interval",interval,unit)
        }

        fun build():NoHttpClient{
            return NoHttpClient(this)
        }
    }

    override fun newCall(request: Request): Call {
        return RealCall(this,request)
    }


}