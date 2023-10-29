#define STB_IMAGE_IMPLEMENTATION

#include <jni.h>
#include <string>

#include <android/asset_manager_jni.h>

#include "include/render.h"
#include "include/ESContext.h"
#include "include/RenderLoop.h"


extern "C"
render* getSelf(JNIEnv* &env, jobject &thiz){
    jclass jcls = env->GetObjectClass(thiz);
    jfieldID jfd = env->GetFieldID(jcls,"nativeRenderHandleId", "J");
    jlong value = env->GetLongField(thiz, jfd);

    return reinterpret_cast<render *>(value);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_init(JNIEnv *env, jobject thiz) {
    jclass jcls = env->GetObjectClass(thiz);

//    bool detached = RectRender::getJvm()->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED;
//    if (detached) {
//        RectRender::getJvm()->AttachCurrentThread(&env, nullptr);
//    }

    jfieldID jfid = env->GetFieldID(jcls, "assetManager", "Landroid/content/res/AssetManager;");
    jobject asset_manager = env->GetObjectField(thiz, jfid);
    AAssetManager *assetManager = AAssetManager_fromJava(env, asset_manager);
    if (assetManager == nullptr) {
        ALOGE("asset manager is nullptr");
    }
    ESContext::self()->setAssetManager(assetManager);

    jfieldID jf_hid = env->GetFieldID(jcls,"nativeRenderHandleId", "J");
    render* instance = ESContext::self()->createRender();
    env->SetLongField(thiz, jf_hid, reinterpret_cast<jlong>(instance));


//    if (detached) {
//        RectRender::getJvm()->DetachCurrentThread();
//    }

}




extern "C"
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    ESContext::setJvm(vm);
    return JNI_VERSION_1_4;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_render(JNIEnv *env, jobject thiz,jfloat green) {

    getSelf(env,thiz)->draw(green);

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
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_destroy(JNIEnv *env, jobject thiz,jlong jid) {
    render* ptr = getSelf(env,thiz);

    jclass jcls = env->GetObjectClass(thiz);
    jfieldID jf_hid = env->GetFieldID(jcls,"nativeRenderHandleId", "J");
    //render* instance = ESContext::self()->createRender();
    env->SetLongField(thiz, jf_hid, (jlong)-1);

    if (ptr){
        delete ptr;
        ptr = nullptr;
    }

}
extern "C"
JNIEXPORT void JNICALL
Java_com_zml_opengl_Render_testLoop(JNIEnv *env, jobject thiz) {
    RenderLoop* renderLoop = new RenderLoop() ;
    renderLoop->postMessageDelay(5,2000);
}