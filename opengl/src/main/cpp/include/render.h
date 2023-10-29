//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_RENDER_H
#define OD_LEARN_RENDER_H

#include <string>
#include <GLES3/gl3.h>
#include <EGL/egl.h>
#include <EGL/eglext.h>
#include <jni.h>
#include <android/asset_manager_jni.h>

#include "../log.h"

class render {

public:

    virtual GLvoid draw(float greenVal) = 0;

    virtual void size(int w,int h) = 0;
    virtual GLint init() = 0;


    virtual ~render(){
        ALOGE("render base destructor");
        glDeleteProgram(mProgram);
        glDeleteVertexArrays(1, &mVAO);
        glDeleteBuffers(1, &mVBO);
    }


protected:
    GLuint mVBO;
    GLuint mVAO;
    GLuint mProgram;

    int width;
    int height;


};


#endif //OD_LEARN_RENDER_H
