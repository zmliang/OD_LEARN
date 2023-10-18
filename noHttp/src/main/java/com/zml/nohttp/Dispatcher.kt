package com.zml.nohttp

import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Dispatcher constructor() {

    private val executorService = ThreadPoolExecutor(0, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
        SynchronousQueue(), defaultThreadFactory("noHttp Dispatcher-", false))


    private fun defaultThreadFactory(threadName:String,daemon:Boolean): ThreadFactory{
        return ThreadFactory { Thread(it,threadName).apply { isDaemon = daemon } }
    }
}