package com.zml.nohttp

class NoHttpClient internal constructor(builder:Builder):INoHttp,Call.Factory{

    private val dispatcher = Dispatcher()
    private val connectTimeout = builder.connectTimeout
    private val readTimeout = builder.readTimeout
    private val writeTimeout = builder.writeTimeout
    private val interceptors = builder.interceptors

    @get:JvmName("retryOnConnectionFailure") val retryOnConnectionFailure: Boolean =
        builder.retryOnConnectionFailure

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

        fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean) = apply {
            this.retryOnConnectionFailure = retryOnConnectionFailure
        }

        fun build():NoHttpClient{
            return NoHttpClient(this)
        }
    }

    override fun newCall(request: Request): Call {
        return RealCall(this,request)
    }


}