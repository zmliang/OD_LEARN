package com.example.base

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class SchedulerProvider {
    fun io(): Scheduler = Schedulers.io()

    fun computation(): Scheduler = Schedulers.computation()

    fun ui(): Scheduler {
        //TODO
        return Schedulers.newThread()
    }
}