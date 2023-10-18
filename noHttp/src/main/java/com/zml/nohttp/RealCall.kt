package com.zml.nohttp

class RealCall(
    val client: NoHttpClient,
    val originRequest: Request
):Call {
    override fun execute(): Response {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled() {
        TODO("Not yet implemented")
    }

    override fun isExecuted() {
        TODO("Not yet implemented")
    }
}