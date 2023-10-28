package com.example.od;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.od.view.MyGLSurfaceView;
import com.zml.opengl.Render;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends Activity {
    private final int CONTEXT_CLIENT_VERSION = 3;

    private MyGLSurfaceView mGLSurfaceView;
    private Render mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setContentView(R.layout.main_activity);

        mGLSurfaceView = MyGLSurfaceView.create(this);
        mRenderer=new Render(this);

        if (!detectOpenGLES30()){
            finish();
            return;
        }

        // 设置OpenGl ES的版本
        mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
        // 设置与当前GLSurfaceView绑定的Renderer
        mGLSurfaceView.setRenderer(mRenderer);
        // 设置渲染的模式
        mGLSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);

        setContentView(mGLSurfaceView);

        //mGLSurfaceView.loopRender();


        mRenderer.testLoop();

    }



    private boolean detectOpenGLES30() {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
        Log.e("ZML","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
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
