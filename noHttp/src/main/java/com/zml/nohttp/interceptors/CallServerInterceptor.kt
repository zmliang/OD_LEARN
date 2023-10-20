package com.zml.nohttp.interceptors

import android.util.Log
import com.zml.nohttp.Interceptor
import com.zml.nohttp.RealInterceptorChain
import com.zml.nohttp.Response

class CallServerInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("zml","CallServerInterceptor")
        val realChain = chain as RealInterceptorChain

        //请求网络
        val response = Response.Builder().build()

        return response
    }

}