package com.pos.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestFlow {

    @Test
    fun flow() {
        runBlocking {
            simpleFlow().collect{
                println(it)
            }
        }
    }

    /**
     * 异步返回多个值
     */
    private fun simpleFlow(): Flow<Int> {
        return flow<Int> {
            for (i in 1..12) {
                emit(i)
            }
        }
    }

}