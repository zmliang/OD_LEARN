package com.pos.ui

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println("========= start ===============")
        GlobalScope.launch {
            println("global scope start")
            test1()
            test2()

            val deferred =async {
                delay(1000)
                "大傻叉"
            }

            val result = deferred.await()

            println("this is in global scope==$result")
        }
        println("=========================")
    }



    suspend fun test1(){
        println("this is test1")
    }

    suspend fun test2(){
        println("this is test2")
    }
}