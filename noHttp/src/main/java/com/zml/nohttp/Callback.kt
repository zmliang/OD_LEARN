package com.zml.nohttp

import java.io.IOException

interface Callback {

    fun onFailure(call: Call, e: IOException)

    @Throws(IOException::class)
    fun onResponse(call: Call, response: Response)

}