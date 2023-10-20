package com.zml.nohttp

class HttpUrl(
    private val scheme:String,
    private val host:String,
    private val port:Int,
    private val fragment:String,
    private val url:String,

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