package com.zml.nohttp

import java.util.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Dispatcher constructor() {

    private val readyAsyncCalls = ArrayDeque<RealCall.AsyncCall>()

    private val runningAsyncCalls = ArrayDeque<RealCall.AsyncCall>()

    private val runningSyncCalls = ArrayDeque<RealCall>()

    private val executorService = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
        SynchronousQueue(), defaultThreadFactory("noHttp Dispatcher-", false))


    @set:Synchronized
    @get:Synchronized
    var idleCallback: Runnable? = null

    private fun defaultThreadFactory(threadName:String,daemon:Boolean): ThreadFactory{
        return ThreadFactory { Thread(it,threadName).apply { isDaemon = daemon } }
    }

    @get:Synchronized var maxRequests = 64
        set(maxRequests) {
            require(maxRequests >= 1) { "max < 1: $maxRequests" }
            synchronized(this) {
                field = maxRequests
            }
            promoteAndExecute()
        }

    @get:Synchronized var maxRequestsPerHost = 5
        set(maxRequestsPerHost) {
            require(maxRequestsPerHost >= 1) { "max < 1: $maxRequestsPerHost" }
            synchronized(this) {
                field = maxRequestsPerHost
            }
            promoteAndExecute()
        }

    @Synchronized internal fun executed(call: RealCall) {
        runningSyncCalls.add(call)
    }


    fun enqueue(call: RealCall.AsyncCall){
        synchronized(this){
            readyAsyncCalls.add(call)
            if (!call.call.forWebSocket){
                val existingCall = findExistingCallWithHost(call.host)
                if (existingCall != null) {
                    call.reuseCallsPerHostFrom(existingCall)
                }
            }
        }
        promoteAndExecute()
    }

    private fun promoteAndExecute():Boolean{
        val executableCalls = mutableListOf<RealCall.AsyncCall>()
        val isRunning: Boolean
        synchronized(this) {
            val i = readyAsyncCalls.iterator()
            while (i.hasNext()) {
                val asyncCall = i.next()

                if (runningAsyncCalls.size >= this.maxRequests) {
                    break
                }
                if (asyncCall.callsPerHost.get() >= this.maxRequestsPerHost) {
                    continue
                }

                i.remove()
                asyncCall.callsPerHost.incrementAndGet()
                executableCalls.add(asyncCall)
                runningAsyncCalls.add(asyncCall)
            }
            isRunning = runningCallsCount() > 0
        }

        for (i in 0 until executableCalls.size) {
            val asyncCall = executableCalls[i]
            asyncCall.executeOn(executorService)
        }

        return isRunning

    }

    fun finished(call: RealCall.AsyncCall){
        call.callsPerHost.decrementAndGet()
        finished(runningAsyncCalls, call)
    }

    internal fun finished(call: RealCall) {
        finished(runningSyncCalls, call)
    }

    private fun <T> finished(calls: Deque<T>, call: T) {
        val idleCallback: Runnable?
        synchronized(this) {
            if (!calls.remove(call)) throw AssertionError("Call wasn't in-flight!")
            idleCallback = this.idleCallback
        }

        val isRunning = promoteAndExecute()

        if (!isRunning && idleCallback != null) {
            idleCallback.run()
        }
    }

    private fun findExistingCallWithHost(host:String):RealCall.AsyncCall?{
        for (existingCall in runningAsyncCalls) {
            if (existingCall.host == host) return existingCall
        }
        for (existingCall in readyAsyncCalls) {
            if (existingCall.host == host) return existingCall
        }
        return null
    }


    @Synchronized fun queuedCallsCount(): Int = readyAsyncCalls.size

    @Synchronized fun runningCallsCount(): Int = runningAsyncCalls.size + runningSyncCalls.size
}