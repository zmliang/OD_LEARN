#include <jni.h>
#include <string>

#include <android/asset_manager_jni.h>

#include "include/ES3Render.h"
#include "include/AssetShader.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_zml_opengl_Render_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_init(JNIEnv *env, jobject thiz) {
    jclass jcls = env->GetObjectClass(thiz);

    bool detached = ES3Render::getJvm()->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED;
    if (detached) {
        ES3Render::getJvm()->AttachCurrentThread(&env, nullptr);
    }

    jfieldID jfid = env->GetFieldID(jcls, "assetManager", "Landroid/content/res/AssetManager;");
    jobject asset_manager = env->GetObjectField(thiz, jfid);
    AAssetManager *assetManager = AAssetManager_fromJava(env, asset_manager);
    if (assetManager == nullptr) {
        ALOGE("asset manager is nullptr");
    }
    AssetShader assetShader = AssetShader(assetManager);
    assetShader.load();
    char *shader = assetShader.read("triangle/vertex");
    ALOGV(shader);

    if (detached) {
        ES3Render::getJvm()->DetachCurrentThread();
    }

}


extern "C"
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    ES3Render::setJvm(vm);
    return JNI_VERSION_1_4;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_render(JNIEnv *env, jobject thiz) {

    ES3Render::self()->draw();

}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_resize(JNIEnv *env, jobject thiz, jint w, jint h) {
    ES3Render::self()->size(w,h);
}