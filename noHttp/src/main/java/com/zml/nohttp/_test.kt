package com.zml.nohttp

import android.util.Log
import okio.Buffer
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.Executors
import javax.net.ssl.SSLSocketFactory

class _test {

    private val threadPool = Executors.newCachedThreadPool()
    private var socket: Socket? = null
    private fun test() {
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
                val os = socket?.getOutputStream()
                val sb = StringBuffer()
                sb.append("GET /article/list/1/json HTTP/1.1\r\n")
                sb.append("Host: ").append(host).append(":").append(port)
                sb.append("\r\n")
                sb.append("Accept: */*\r\n")
                sb.append("Connection: Keep-Alive\r\n")
                sb.append("\r\n")
                Log.i("zml", sb.toString())
                os?.write(sb.toString().toByteArray())
                os?.flush()
                val `is` = socket?.getInputStream()
                //定义一个容量范围
                val bys = ByteArray(1024)
                var len: Int
                val buffer = Buffer()
                while (`is`?.read(bys).also { len = it!! } != -1) {
                    buffer.write(bys)
                    //                    String data = new String(bys,0,len);
//                    Log.e("zml","--> "+data);
                }
                Log.e("zml", "--> " + buffer.readUtf8())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}