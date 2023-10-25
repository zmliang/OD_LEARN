#include <jni.h>
#include <string>

#include "include/ES3Render.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_zml_opengl_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";


    ES3Render render;
    render.createWindow(0,0);


    return env->NewStringUTF(hello.c_str());
}