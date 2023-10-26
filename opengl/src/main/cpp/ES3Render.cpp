//
// Created by zml on 2023/10/25.
//

#include "include/ES3Render.h"



GLint ES3Render::init() {

}

GLuint ES3Render::loadShader(const GLenum type, const char *shaderSrc) {
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


JavaVM* ES3Render::jvm = nullptr;

JavaVM *ES3Render::getJvm() {
    return jvm;
}

void ES3Render::setJvm(JavaVM *javaVM) {
    jvm = javaVM;
}


GLboolean ES3Render::createWindow(GLint width, GLint height)
{
    ALOGE("createWindow");

    return GL_TRUE;
}

GLvoid ES3Render::draw()
{
    ALOGE("ES3Render draw");
}


ES3Render::~ES3Render() {

}
