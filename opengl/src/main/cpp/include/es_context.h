//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES_CONTEXT_H
#define OD_LEARN_ES_CONTEXT_H

#include <string>
#include <GLES3/gl3.h>
#include <EGL/egl.h>
#include <EGL/eglext.h>

#include "../log.h"

class es_context {

public:

    virtual GLboolean createWindow(GLint width,GLint height) = 0;

    virtual GLvoid draw() = 0;

    virtual ~es_context(){
        ALOGE("es_context base destructor");
    }


protected:
    GLuint mVBO;
    GLuint mVAO;
    GLuint mProgram;

public:
    static const unsigned int SC_WIDTH = 800;
    static const unsigned int SC_HEIGHT = 600;

};


#endif //OD_LEARN_ES_CONTEXT_H
