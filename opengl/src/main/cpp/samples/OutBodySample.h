//
// Created by zml on 2023/11/20.
//

#ifndef OD_LEARN_OUTBODYSAMPLE_H
#define OD_LEARN_OUTBODYSAMPLE_H

#include "../inc/render.h"
#include "../inc/ESContext.h"


class OutBodySample: public render{

public:
    GLvoid draw(float greenVal);

    GLint init();

    ~OutBodySample();

private:
    void loadTexture( GLuint &textureId, const char* textureSource);

};


#endif //OD_LEARN_OUTBODYSAMPLE_H
