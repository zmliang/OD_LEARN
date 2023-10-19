package com.zml.nohttp.interceptors

import com.zml.nohttp.Interceptor
import com.zml.nohttp.RealInterceptorChain
import com.zml.nohttp.Response

class CallServerInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val realChain = chain as RealInterceptorChain


    }

}