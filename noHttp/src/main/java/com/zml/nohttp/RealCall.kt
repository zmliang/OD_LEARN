package com.zml.nohttp

import android.util.Log
import com.zml.nohttp.interceptors.BridgeInterceptor
import com.zml.nohttp.interceptors.CallServerInterceptor
import com.zml.nohttp.interceptors.RetryAndFollowUpInterceptor
import okio.Timeout
import java.io.IOException
import java.io.InterruptedIOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class RealCall(
    val client: NoHttpClient,
    val originRequest: Request,
    val forWebSocket: Boolean = false
):Call {

    private val executed = AtomicBoolean()

    @Volatile private var canceled = false

    private var exchangeFinder: ExchangeFinder? = null
    fun enterNetworkInterceptorExchange(request: Request, newExchangeFinder: Boolean){
        if (newExchangeFinder) {
//            this.exchangeFinder = ExchangeFinder(
//                connectionPool,
//                createAddress(request.url),
//                this,
//               // eventListener
//            )
        }
    }

    override fun execute(): Response {
        check(executed.compareAndSet(false, true)) { "Already Executed" }

        try {
            client.dispatcher().executed(this)
            return getResponseWithInterceptorChain()
        } finally {
            client.dispatcher().finished(this)
        }
    }

    override fun enqueue(callback: Callback) {
        check(executed.compareAndSet(false, true)) { "Already Executed" }

        client.dispatcher().enqueue(AsyncCall(callback))
    }

    override fun cancel() {
        if (canceled){
            return
        }
        canceled = true

    }

    override fun isCanceled() = canceled

    override fun isExecuted():Boolean {
        return executed.get()
    }

    override fun timeout(): Timeout {
        return Timeout()
    }

    fun getResponseWithInterceptorChain():Response{
        val interceptors = mutableListOf<Interceptor>()
        interceptors+=client.interceptors()
        Log.i("zml","interceptors=$interceptors")
        interceptors += RetryAndFollowUpInterceptor(client)
        interceptors += BridgeInterceptor()
        interceptors += CallServerInterceptor()

        val chain = RealInterceptorChain(this,interceptors,0,originRequest,client.readTimeout(),client.readTimeout(),client.connectTimeout())

        val response = chain.processed(originRequest)

        return response
    }

    inner class AsyncCall(
        private val callback:Callback
    ):Runnable{

        fun executeOn(executorService: ExecutorService) {
           // client.dispatcher().assertThreadDoesntHoldLock()

            var success = false
            try {
                executorService.execute(this)
                success = true
            } catch (e: RejectedExecutionException) {
                val ioException = InterruptedIOException("executor rejected")
                ioException.initCause(e)
                //noMoreExchanges(ioException)
                callback.onFailure(this@RealCall, ioException)
            } finally {
                if (!success) {
                    client.dispatcher().finished(this)
                }
            }
        }

        @Volatile var callsPerHost = AtomicInteger(0)
            private set

        fun reuseCallsPerHostFrom(other: AsyncCall) {
            this.callsPerHost = other.callsPerHost
        }

        val host: String
            get() = originRequest.url.host

        val request: Request
            get() = originRequest

        val call: RealCall
            get() = this@RealCall

        override fun run() {
            try {

                val responde = getResponseWithInterceptorChain()
                callback.onResponse(call,responde)

            }catch (e: IOException){
                callback.onFailure(call,e)
            }catch (t:Throwable){
                cancel()
                callback.onFailure(call, IOException("cancel due to $t"))
            } finally {
                client.dispatcher().finished(this)
            }
        }

    }
}