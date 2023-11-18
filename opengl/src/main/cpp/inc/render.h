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
#include "stb_image.h"

#include "../log.h"

class render {

public:

    virtual GLvoid draw(float greenVal) = 0;

    void size(int w,int h){
        this->width = w;
        this->height = h;

        glViewport ( 0, 0, width, height );
    }
    virtual GLint init() = 0;


    virtual ~render(){
        ALOGE("render base destructor");
        glDeleteProgram(mProgram);
        glDeleteVertexArrays(1, &mVAO);
        glDeleteBuffers(1, &mVBO);
    }


protected:
    GLuint loadShader(GLenum type,const char *shaderSrc){
        GLuint shader;
        GLint compiler;

        shader = glCreateShader(type);
        if (shader == 0){
            return 0;
        }

        glShaderSource(shader,1,&shaderSrc,NULL);

        glCompileShader(shader);

        glGetShaderiv(shader,GL_COMPILE_STATUS,&compiler);
        if (!compiler){
            GLint logLen;
            glGetShaderiv(shader,GL_INFO_LOG_LENGTH,&logLen);
            if ( logLen > 1 )
            {
                char *infoLog = (char *)malloc ( sizeof ( char ) * logLen );
                glGetShaderInfoLog ( shader, logLen, NULL, infoLog );
                ALOGE("Error compiling shader:[%s]", infoLog );

                free ( infoLog );
            }

            glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    GLuint mVBO;
    GLuint mVAO;
    GLuint mEBO;
    GLuint mTexture;
    GLuint mProgram;

    int width;
    int height;


};


#endif //OD_LEARN_RENDER_H
