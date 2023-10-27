package com.zml.opengl;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Render implements GLSurfaceView.Renderer {
    private final String TAG = "ZML_render";

    private long nativeRenderHandleId = -1;

    static {
        System.loadLibrary("gles");
    }

    private AssetManager assetManager;

    public Render(Context context){
        this.assetManager = context.getAssets();
        this.init();
    }

    native void init();

    native void render();

    native void resize(int w,int h);

    native void create();

    //native void destroy(long handleId);

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