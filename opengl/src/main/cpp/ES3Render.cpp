//
// Created by zml on 2023/10/25.
//

#include "include/ES3Render.h"



GLint ES3Render::init() {

}

void ES3Render::size(int w, int h) {
    this->width = w;
    this->height = h;
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
    float vertices[] = {
            0.5f,  0.5f, 0.0f,  // top right
            0.5f, -0.5f, 0.0f,  // bottom right
            -0.5f, -0.5f, 0.0f,  // bottom left

    };

    // Set the viewport
    glViewport ( 0, 0, width, height );

    // Clear the color buffer
    glClear ( GL_COLOR_BUFFER_BIT );

    // Use the program object
    glUseProgram ( g_programObject );

    // Load the vertex data
    glVertexAttribPointer ( 0, 3, GL_FLOAT, GL_FALSE, 0, vertices );
    glEnableVertexAttribArray ( 0 );

    glDrawArrays ( GL_TRIANGLES, 0, 3 );
}


ES3Render::~ES3Render() {

}
