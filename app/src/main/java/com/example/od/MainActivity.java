package com.example.od;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zml.nohttp._test;
import com.zml.opengl.NativeLib;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ZML","StringFromJNI="+new NativeLib().stringFromJNI());
        setContentView(R.layout.main_activity);

        new _test().test();

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
