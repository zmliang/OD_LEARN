//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "render.h"
#include <atomic>


class ES3Render : public render{

public:
    static std::unique_ptr<ES3Render> instance()//线程安全单例
    {
        std::unique_ptr<ES3Render> ptr(new ES3Render());
        return ptr;
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
    void loop();

private:
    int width;
    int height;

    GLuint mProgram;

    AAssetManager *mAssetManager = nullptr;

    std::atomic<bool> loopFlag;


public:
    ES3Render(): loopFlag(false){

    }


};


#endif //OD_LEARN_ES3RENDER_H
