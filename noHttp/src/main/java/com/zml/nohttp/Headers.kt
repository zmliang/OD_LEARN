package com.zml.nohttp

import java.util.ArrayList

class Headers(
    private val namesAndValues:Array<String>
) : Iterable<Pair<String, String>> {

    private fun size():Int{
        return namesAndValues.size/2
    }


    private fun name(index:Int):String{
        return namesAndValues[index*2]
    }

    private fun value(index: Int):String{
        return namesAndValues[index*2+1]
    }

    override fun iterator(): Iterator<Pair<String, String>> {
        return Array(size()){name(it) to value(it)}.iterator()
    }

    fun newBuilder():Builder{
        val builder = Builder()
        builder

        return builder
    }


    class Builder{
        internal val namesAndValues: MutableList<String> = ArrayList(20)


        fun add(name: String, value: String) = apply {
            addLenient(name, value)
        }
        private fun addLenient(name: String, value: String) = apply {
            namesAndValues.add(name)
            namesAndValues.add(value.trim())
        }

        fun set(name: String, value: String) = apply {
            addLenient(name, value)
        }

        fun build():Headers{
            return Headers(this.namesAndValues.toTypedArray())
        }

    }
}