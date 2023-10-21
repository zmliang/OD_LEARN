package com.zml.nohttp

class Request internal constructor(
    val url: HttpUrl,
    val method: String,
    val headers: Headers,
    @get:JvmName("body") val body: RequestBody?,
){


    fun newBuilder(): Builder = Builder(this)

    open class Builder{
        private var url: HttpUrl? = null
        private var method: String
        private var headers: Headers.Builder
        private var body: RequestBody? = null

        constructor(){
            this.method = "GET"
            this.headers = Headers.Builder()
        }
        constructor(request: Request){
            this.url = request.url
            this.method = request.method;
            this.headers = request.headers.newBuilder()
            this.body = request.body
        }

        open fun  url(_url:String):Builder{
            this.url = HttpUrl.Builder()
                .build()
            return this
        }


        open fun build():Request{
            return Request(
                checkNotNull(url) { "url == null" },
                method,
                headers.build(),
                body,
            )
        }
    }

}