package com.zml.opengl;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Render implements GLSurfaceView.Renderer {
    private final String TAG = "native_render";


    static {
        System.loadLibrary("opengl");
    }

    private AssetManager assetManager;

    public Render(Context context){
        this.assetManager = context.getAssets();
        this.init();
    }

    public native String stringFromJNI();

    native void init();

    native void render();

    native void resize(int w,int h);

    native void create();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.resize(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        this.render();
    }


}