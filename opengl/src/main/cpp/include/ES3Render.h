//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "render.h"
#include <jni.h>
#include <android/asset_manager_jni.h>



class ES3Render : public render{

public:
    static ES3Render* self()//线程安全单例
    {
        static ES3Render inst_;
        return &inst_;
    }

public:
    void size(int w,int h);
    GLvoid draw();
    GLuint loadShader(GLenum type,const char *shaderSrc);
    GLint init();
    ~ES3Render();

    void assetManager(AAssetManager *am);


private:
    char* read(const char *srcName);

private:
    int width;
    int height;

    GLuint mProgram;

    AAssetManager *mAssetManager = nullptr;

private:
    ES3Render(){}


};


#endif //OD_LEARN_ES3RENDER_H
