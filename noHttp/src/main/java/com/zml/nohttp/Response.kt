package com.zml.nohttp

class Response {

    var request:Request? = null
    fun newBuilder(): Builder = Builder(this)

    fun request(request: Request){
        this.request = request
    }

    open class Builder{
        var request:Request? = null

        constructor(){

        }
        constructor(response: Response){

        }
        fun request(request: Request):Builder{
            this.request = request
            return this
        }

        open fun build():Response{
            return Response()
        }
    }

}