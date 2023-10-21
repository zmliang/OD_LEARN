package com.example.od;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zml.nohttp.NoHttpClient;
import com.zml.nohttp._test;
import com.zml.opengl.NativeLib;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ZML","StringFromJNI="+new NativeLib().stringFromJNI());
        setContentView(R.layout.main_activity);


        NoHttpClient noHttpClient1 = new NoHttpClient.Builder().build();
        com.zml.nohttp.Request request1 = new com.zml.nohttp.Request.Builder()
                .url("_TEST")
                .build();

        noHttpClient1.newCall(request1).enqueue(new com.zml.nohttp.Callback() {
            @Override
            public void onFailure(@NonNull com.zml.nohttp.Call call, @NonNull IOException e) {
                Log.i("zml","call="+call.toString()+"e="+e.toString());
            }

            @Override
            public void onResponse(@NonNull com.zml.nohttp.Call call, @NonNull com.zml.nohttp.Response response) throws IOException {
                Log.i("zml","call="+call.toString()+"; response = "+response.toString());
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private final OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/article/list/1/json")
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.i("zml","\r\n\r\n");
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.i("zml",responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.i("zml",response.body().string());
            }
        });
    }


}
