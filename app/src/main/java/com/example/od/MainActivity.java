package com.example.od;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zml.opengl.NativeLib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLSocketFactory;

import okio.Buffer;

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
                Buffer buffer = new Buffer();
                while ((len = is.read(bys))!=-1){
                    buffer.write(bys);
//                    String data = new String(bys,0,len);
//                    Log.e("zml","--> "+data);
                }
                Log.e("zml","--> "+buffer.readUtf8());


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
//
//    private final OkHttpClient client = new OkHttpClient();
//
//    public void run() throws Exception {
//        Request request = new Request.Builder()
//                .url("https://www.wanandroid.com/article/list/1/json")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                Log.i("zml","\r\n\r\n");
//                Headers responseHeaders = response.headers();
//                for (int i = 0; i < responseHeaders.size(); i++) {
//                    Log.i("zml",responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
//
//                Log.i("zml",response.body().string());
//            }
//        });
//    }


}
