//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_RENDER_H
#define OD_LEARN_RENDER_H

#include <string>
#include <GLES3/gl3.h>
#include <EGL/egl.h>
#include <EGL/eglext.h>

#include "../log.h"

class render {

public:

    virtual GLvoid draw() = 0;

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


};


#endif //OD_LEARN_RENDER_H
