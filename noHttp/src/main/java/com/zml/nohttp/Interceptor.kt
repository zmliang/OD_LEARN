package com.zml.nohttp

import java.io.IOException
import java.util.concurrent.TimeUnit

interface Interceptor {
    @Throws(IOException::class)
    fun intercept(chain: Chain): Response


    interface Chain{
        fun request() : Request
        @Throws(IOException::class)
        fun processed(request: Request):Response

        fun connection():Connection

        fun call():Call

        fun connectTimeoutMillis(): Int

        fun withConnectTimeout(timeout: Int, unit: TimeUnit): Chain

        fun readTimeoutMillis(): Int

        fun withReadTimeout(timeout: Int, unit: TimeUnit): Chain

        fun writeTimeoutMillis(): Int

        fun withWriteTimeout(timeout: Int, unit: TimeUnit): Chain
    }

}