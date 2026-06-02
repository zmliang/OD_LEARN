package com.pos.od

import android.util.Log
import com.north.ws.IWebSocket
import com.north.ws.SocketEvent
import com.north.ws.newWebSocket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.coroutines.Continuation

import zlc.season.rxdownload4.download
import zlc.season.rxdownload4.file
import zlc.season.rxdownload4.utils.safeDispose

class T1 {
    suspend fun log():String{
        delay(10000)
        return "这是一个字符串"
    }

    fun useAppContext() {
        GlobalScope.launch {
            val s = log()
        }

    }


    fun test(){
        //val url = "http://119.147.42.82:38660/hm-dev/flame-release/app/mobile/zip/dev/100028/offlinePkg/20000000/20250108/total/202501080.zip"
        val url = "https://dev-pkg.finmall.com/filestore/dev-public-flame/mobile/zip/dev/100001/offlinePkg/10001001/2025043001/total/20250430010.zip"
        val disposable = url.download()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { progress ->
                    //download progress
                   Log.d("" +
                           "", "progress: $progress")
                },
                onComplete = {
                    //download complete
                    Log.d("ZMLIANG", "onComplete:")
                },
                onError = {
                    //download failed
                    Log.d("ZMLIANG", "onError: $it")
                }
            )

    }

}