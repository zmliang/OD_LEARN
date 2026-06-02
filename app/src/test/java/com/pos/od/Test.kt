package com.pos.od

import org.junit.Test


data class Book(
    val name: String,
)
class Test1 {


    @Test
    fun testBook() {
        val book = Book("Android 开发艺术探索.heif")

        val b = book.name.run {
            this.replace(".heif", ".pdf")
        }

        println("---------------------")
        println(b)
        println(book.name)

    }




}