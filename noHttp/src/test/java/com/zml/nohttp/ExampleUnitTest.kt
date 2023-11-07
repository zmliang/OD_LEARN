package com.zml.nohttp

import com.zml.nohttp.Outter.Inner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        GlobalScope.launch {
            withContext(Dispatchers.IO){
                println("这是个携程中运行的代码块")
            }
        }

    }
}