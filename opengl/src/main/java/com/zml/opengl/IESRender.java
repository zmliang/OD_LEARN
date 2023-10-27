package com.zml.opengl;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public interface IESRender extends GLSurfaceView.Renderer{



    interface Factory{

        IESRender createRender();

        void destroyRender();

    }

}
