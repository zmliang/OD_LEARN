package com.example.od;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zml.opengl.NativeLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends Activity {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private Socket socket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ZML","StringFromJNI="+new NativeLib().stringFromJNI());
        setContentView(R.layout.main_activity);

        this.test();

    }

    private void test(){

        threadPool.execute(() -> {
            socket = new Socket();

            try {
                InetAddress[] addresses = InetAddress.getAllByName("www.wanandroid.com");
                String host = addresses[0].getHostAddress();
                Log.i("zml","host = "+host);
                int port = 443;

                socket = SSLSocketFactory.getDefault().createSocket(host, port);
                //socket.connect(new InetSocketAddress(host,port));

                Log.i("zml","isConnected="+socket.isConnected());

                OutputStream os = socket.getOutputStream();

                StringBuffer sb = new StringBuffer();
                sb.append("GET /article/list/1/json HTTP/1.1\r\n");
                sb.append("Host: ").append(host).append(":").append(port);
                sb.append("\r\n");
                sb.append("Accept: */*\r\n");
                sb.append("Connection: Keep-Alive\r\n");
                sb.append("\r\n");

                Log.i("zml",sb.toString());
                os.write(sb.toString().getBytes());
                os.flush();


                InputStream is = socket.getInputStream();
                //定义一个容量范围
                byte[] bys = new byte[1024];
                int len;
                while ((len = is.read(bys))!=-1){
                    String data = new String(bys,0,len);
                    Log.e("zml","--> "+data);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}
