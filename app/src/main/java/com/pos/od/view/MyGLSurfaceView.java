package com.pos.od.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.zml.opengl.Render;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyGLSurfaceView extends GLSurfaceView {

    Render _render;

    private InnerThread innerThread = new InnerThread(this);

    public void loopRender(Render renderer){
        _render = renderer;
        innerThread.loop();
    }

    public static MyGLSurfaceView create(Context context){

        MyGLSurfaceView instance = new MyGLSurfaceView(context);
        return instance;
    }

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    static final class InnerThread extends Thread{
        final int REQ_RENDER = 0X01;
        final int TIMEOUT_SEC = 2*1000;

        final int DURATION = 1;
        static Handler handler;
        CountDownLatch countDownLatch = new CountDownLatch(1);

        @Override
        public void run() {

            Looper.prepare();
            handler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what){
                        case REQ_RENDER:
                            InnerThread.this.weakReference.get()._render.setGreenValue((float) (Math.sin(Float.valueOf((System.currentTimeMillis())/1000))/2.0f+0.5f));
                            InnerThread.this.weakReference.get().requestRender();
                            InnerThread.this.sendRenderMessage();
                            break;
                    }
                }
            };
            countDownLatch.countDown();

            Looper.loop();
        }

        void loop(){
            start();
            sendRenderMessage();
        }

        void sendRenderMessage(){
            try {
                if (countDownLatch.getCount()!=0){
                    countDownLatch.await(TIMEOUT_SEC, TimeUnit.MILLISECONDS);
                }
                handler.sendEmptyMessageDelayed(REQ_RENDER,DURATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        WeakReference<MyGLSurfaceView> weakReference;
        InnerThread(MyGLSurfaceView view){
            weakReference = new WeakReference<>(view);
        }
    }
}
