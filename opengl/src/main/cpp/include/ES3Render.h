//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "es_context.h"
#include <jni.h>
#include <android/asset_manager_jni.h>



class ES3Render : public es_context{

public:
    static ES3Render* self()
    {
        static ES3Render inst_;
        return &inst_;
    }

public:
    void size(int w,int h);
    GLboolean createWindow(GLint width,GLint height);
    GLvoid draw();
    GLuint loadShader(GLenum type,const char *shaderSrc);
    GLint init();
    ~ES3Render();

    void assetManager(AAssetManager *am);

public:
    static void setJvm(JavaVM *javaVM);
    static JavaVM* getJvm();

private:
    char* read(const char *srcName);

private:
    int width;
    int height;

    GLuint mProgram;

    AAssetManager *mAssetManager = nullptr;

private:
    ES3Render(){}
    static JavaVM *jvm;

};


#endif //OD_LEARN_ES3RENDER_H
