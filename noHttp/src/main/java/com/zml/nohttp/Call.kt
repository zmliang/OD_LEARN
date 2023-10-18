package com.zml.nohttp

interface Call {

    fun execute():Response

    fun enqueue(callback: Callback)

    fun cancel()

    fun isCanceled()

    fun isExecuted()

    interface Factory{
        fun newCall(request: Request):Call
    }

}