//
// Created by zml on 2023/11/21.
//

#ifndef OD_LEARN_JOGGLESAMPLE_H
#define OD_LEARN_JOGGLESAMPLE_H

#include "../inc/render.h"
#include "../inc/ESContext.h"


class JoggleSample :public render{


public:
    GLvoid draw(float greenVal);

    GLint init();

    ~JoggleSample();

private:
    void loadTexture( GLuint &textureId, const char* textureSource);

};


#endif //OD_LEARN_JOGGLESAMPLE_H
