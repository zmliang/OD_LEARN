package com.zml.nohttp

import java.util.concurrent.TimeUnit

fun checkDuration(name: String, duration: Long, unit: TimeUnit?): Int {
    check(duration >= 0L) { "$name < 0" }
    check(unit != null) { "unit == null" }
    val millis = unit.toMillis(duration)
    require(millis <= Integer.MAX_VALUE) { "$name too large." }
    require(millis != 0L || duration <= 0L) { "$name too small." }
    return millis.toInt()
}