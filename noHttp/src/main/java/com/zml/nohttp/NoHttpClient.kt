package com.zml.nohttp

class NoHttpClient internal constructor(builder:Builder):INoHttp,Call.Factory{

    private val dispatcher = Dispatcher()

    fun dispatcher() = dispatcher



    class Builder constructor(){
        internal var connectTimeout = 10_000
        internal var readTimeout = 10_000
        internal var writeTimeout = 10_000
        internal val interceptors: MutableList<Interceptor> = mutableListOf()


        fun build():NoHttpClient{
            return NoHttpClient(this)
        }
    }

    override fun newCall(request: Request): Call {
        return RealCall(this,request)
    }
}