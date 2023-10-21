package com.zml.nohttp

import okio.Timeout

interface Call {

    fun execute():Response

    fun enqueue(callback: Callback)

    fun cancel()

    fun isCanceled():Boolean

    fun isExecuted():Boolean

    fun timeout():Timeout

    interface Factory{
        fun newCall(request: Request):Call
    }

}