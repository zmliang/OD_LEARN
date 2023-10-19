package com.zml.nohttp

import java.util.concurrent.atomic.AtomicBoolean

class RealCall(
    val client: NoHttpClient,
    val originRequest: Request
):Call {

    private val executed = AtomicBoolean()
    override fun execute(): Response {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback) {
        client.dispatcher().enqueue(this)
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