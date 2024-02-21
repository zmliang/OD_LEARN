package com.pos.od

import android.app.Activity
import android.os.Bundle
import com.example.od.R
import com.zml.v8.v8native

class V8Activity:Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v8_activity)
        v8native().obj()
    }

}