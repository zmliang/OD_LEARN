package com.zml.nohttp

import java.util.Objects
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TaskRunner {

    val backend = Backend { runnable ->
        Thread(runnable, "backend").apply {
            isDaemon = true
        }
    }

    class Backend(threadFactory: ThreadFactory){
        private val executor = ThreadPoolExecutor(
            0,
            Int.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            SynchronousQueue(),
            threadFactory
        )

        fun coordinatorNotify(taskRunner: TaskRunner) {
            taskRunner.notify()
        }

        fun coordinatorWait(taskRunner: TaskRunner, nanos: Long) {
            val ms = nanos / 1_000_000L
            val ns = nanos - (ms * 1_000_000L)
            if (ms > 0L || nanos > 0) {
                (taskRunner as Object).wait(ms, ns.toInt())
            }
        }

        fun nanoTime() = System.nanoTime()

        fun execute(runnable: Runnable) {
            executor.execute(runnable)
        }

        fun shutdown() {
            executor.shutdown()
        }

    }
}