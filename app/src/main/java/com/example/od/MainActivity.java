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
                InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
                String host = addresses[0].getHostAddress();
                Log.i("zml","host = "+host);
                int port = 443;

                socket = SSLSocketFactory.getDefault().createSocket(host, port);
                //socket.connect(new InetSocketAddress(host,port));

                Log.i("zml","isConnected="+socket.isConnected());

                OutputStream os = socket.getOutputStream();

                StringBuffer sb = new StringBuffer();
                sb.append("GET / HTTP/1.1\r\n");
                sb.append("Host: ").append(host).append(":").append(port);
                sb.append("\r\n");
                sb.append("Accept: text/html\r\n");
                sb.append("Connection: Keep-Alive\r\n");
                sb.append("\r\n");
                sb.append("");

                Log.i("zml",sb.toString());
                os.write(sb.toString().getBytes());
                os.flush();


                BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String tmp = "";
                while ((tmp = inReader.readLine()) != null) {
                    // 解析服务器返回的数据，做相应的处理
                    Log.e("zml",tmp);
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
