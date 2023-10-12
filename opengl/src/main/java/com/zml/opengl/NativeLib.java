package com.zml.opengl;

public class NativeLib {

    // Used to load the 'opengl' library on application startup.
    static {
        System.loadLibrary("opengl");
    }

    /**
     * A native method that is implemented by the 'opengl' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}