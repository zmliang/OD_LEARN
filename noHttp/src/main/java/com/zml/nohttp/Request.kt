package com.zml.nohttp

class Request {


    fun newBuilder(): Builder = Builder(this)

    open class Builder{

        constructor(){}
        constructor(request: Request){

        }


        open fun build():Request{
            return Request()
        }
    }

}