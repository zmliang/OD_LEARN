#include <jni.h>
#include <string>

#include <android/asset_manager_jni.h>

#include "include/ES3Render.h"
#include "include/ESContext.h"


extern "C"
ES3Render* getSelf(JNIEnv* &env, jobject &thiz){
    jclass jcls = env->GetObjectClass(thiz);
    jfieldID jfd = env->GetFieldID(jcls,"nativeRenderHandleId", "J");
    jlong value = env->GetLongField(thiz, jfd);

    return reinterpret_cast<ES3Render *>(value);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_init(JNIEnv *env, jobject thiz) {
    jclass jcls = env->GetObjectClass(thiz);

//    bool detached = ES3Render::getJvm()->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED;
//    if (detached) {
//        ES3Render::getJvm()->AttachCurrentThread(&env, nullptr);
//    }

    jfieldID jfid = env->GetFieldID(jcls, "assetManager", "Landroid/content/res/AssetManager;");
    jobject asset_manager = env->GetObjectField(thiz, jfid);
    AAssetManager *assetManager = AAssetManager_fromJava(env, asset_manager);
    if (assetManager == nullptr) {
        ALOGE("asset manager is nullptr");
    }

    jfieldID jf_hid = env->GetFieldID(jcls,"nativeRenderHandleId", "J");
    ES3Render* instance = ES3Render::self();
    env->SetLongField(thiz, jf_hid, reinterpret_cast<jlong>(instance));

    instance->assetManager(assetManager);

//    if (detached) {
//        ES3Render::getJvm()->DetachCurrentThread();
//    }

}




extern "C"
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    ESContext::setJvm(vm);
    return JNI_VERSION_1_4;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_render(JNIEnv *env, jobject thiz) {

    getSelf(env,thiz)->draw();

}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_resize(JNIEnv *env, jobject thiz, jint w, jint h) {
    getSelf(env,thiz)->size(w,h);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_create(JNIEnv *env, jobject thiz) {
    getSelf(env,thiz)->init();
}