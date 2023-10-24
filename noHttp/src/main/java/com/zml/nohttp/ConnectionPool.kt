package com.zml.nohttp

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

class ConnectionPool(
    private val maxIdleConnection:Int = 5,
    private var keepAlive:Long = TimeUnit.MINUTES.toNanos(5)
) {
    init {
        require(keepAlive > 0L) { "keepAliveDuration <= 0: $keepAlive" }
    }
    constructor():this(5,5)

    private val connections = ConcurrentLinkedQueue<RealConnection>()


    fun idleConnectionCount(): Int {
        return connections.count {
            synchronized(it) { it.calls.isEmpty() }
        }
    }

    fun connectionCount(): Int {
        return connections.size
    }



}