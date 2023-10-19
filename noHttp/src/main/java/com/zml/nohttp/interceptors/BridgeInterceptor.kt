package com.zml.nohttp.interceptors

import com.zml.nohttp.Interceptor
import com.zml.nohttp.Response

class BridgeInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userRequest = chain.request()
        val requestBuilder = userRequest.newBuilder()
        //...添加header
        //todo

        val networkResponse = chain.processed(requestBuilder.build())

        val responseBuilder = networkResponse.newBuilder()
            .request(userRequest)

        //...添加respone的东西
        //todo

        return responseBuilder.build()

    }
}