//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "es_context.h"
#include <jni.h>

typedef struct {
    GLuint programObject;
} UserData;

class ES3Render : public es_context{


public:
    GLboolean createWindow(GLint width,GLint height);

    GLvoid draw();

    GLuint loadShader(GLenum type,const char *shaderSrc);

    GLint init();

    ~ES3Render();

public:
    static void setJvm(JavaVM *javaVM);

    static JavaVM* getJvm();

private:
    static JavaVM *jvm;

};


#endif //OD_LEARN_ES3RENDER_H
