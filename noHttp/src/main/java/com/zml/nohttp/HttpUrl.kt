package com.zml.nohttp

class HttpUrl(
    val scheme:String,
    val host:String,
    val port:Int,
    val fragment:String,
    val url:String,

) {


    class Builder{
        private var scheme:String? = null
        private var host:String? = null
        private var port:Int = -1
        private var fragment:String? = null

        open fun scheme(_scheme: String):Builder = apply {
            this.scheme = _scheme
        }

        open fun host(_host:String):Builder{
            this.host = _host
            return this
        }

        open fun fragment():Builder{

            return this
        }

        fun build():HttpUrl{
            return HttpUrl(
                this.scheme?:"_scheme",
                this.host?:"_host",
                this.port,
                this.fragment?:"_fragment",
                toString()
            )
        }
    }
}