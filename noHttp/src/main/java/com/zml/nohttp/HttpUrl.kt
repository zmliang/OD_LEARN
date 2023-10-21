package com.zml.nohttp

class HttpUrl(
    val scheme:String,
    val host:String,
    val port:Int,
    val fragment:String,
    val url:String,

) {


    class Builder{
        private val scheme:String? = null
        private val host:String? = null
        private val port:Int = -1
        private val fragment:String? = null

        fun build():HttpUrl{
            return HttpUrl(
                this.scheme!!,
                this.host!!,
                this.port,
                this.fragment!!,
                toString()
            )
        }
    }
}