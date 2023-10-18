package com.zml.nohttp

import android.util.Log
import okio.Buffer
import okio.ByteString
import okio.sink
import okio.source
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.Executors
import javax.net.ssl.SSLSocketFactory

class _test {

    private val threadPool = Executors.newCachedThreadPool()
    private var socket: Socket? = null
    fun test() {
        threadPool.execute {
            socket = Socket()
            try {
                val addresses =
                    InetAddress.getAllByName("www.wanandroid.com")
                val host = addresses[0].hostAddress
                Log.i("zml", "host = $host")
                val port = 443
                socket = SSLSocketFactory.getDefault().createSocket(host, port)
                //socket.connect(new InetSocketAddress(host,port));
                Log.i("zml", "isConnected=" + socket?.isConnected())
                val _str = buildReq(host,port.toString(),"article/list/1/json")

                /*
                val os = socket?.getOutputStream()
                os?.write(_str.toString().toByteArray())
                os?.flush()
                */

                val sink = socket?.sink()
                val _buffer = Buffer()
                _buffer.write(_str.toString().toByteArray())
                sink?.write(_buffer,_buffer.size)
                sink?.flush()

                val source = socket?.source()
                val _buf = Buffer()

                var len:Long
                while (source?.read(_buf,1024).also { len=it!! }!=-1L){

                }


//                Log.e("zml", "" + _buf.readUtf8LineStrict())
//                Log.e("zml", "" + _buf.readUtf8LineStrict())

                var line:String
                while (_buf.readUtf8LineStrict().also { line = it }.isNotBlank()){
                    Log.e("zml", "" + line)
                }

                Log.e("zml", "" + _buf.readUtf8Line())
                Log.e("zml", "" + _buf.readUtf8Line())
                Log.e("zml", "" + _buf.readUtf8Line())
                /*
                val `is` = socket?.getInputStream()
                //定义一个容量范围
                val bys = ByteArray(1024)
                var len: Int
                val buffer = Buffer()
                while (`is`?.read(bys).also { len = it!! } != -1) {
                    buffer.write(bys)
                }
                Log.e("zml", "--> " + buffer.readUtf8())
                */
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun buildReq(host:String,port:String,path:String):StringBuffer{
        val sb = StringBuffer()
        sb.append("GET /").append(path).append(" HTTP/1.1\r\n")
        sb.append("Host: ").append(host).append(":").append(port)
        sb.append("\r\n")
        sb.append("Accept: */*\r\n")
        sb.append("Connection: Keep-Alive\r\n")
        sb.append("\r\n")
        return sb
    }


}