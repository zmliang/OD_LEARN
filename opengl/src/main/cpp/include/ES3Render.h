//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "render.h"
#include <atomic>
#include "ESContext.h"


class ES3Render : public render{

public:
    void size(int w,int h);
    GLvoid draw(float greenVal);
    GLuint loadShader(GLenum type,const char *shaderSrc);
    GLint init();
    ~ES3Render();

};


#endif //OD_LEARN_ES3RENDER_H
