//
// Created by zml on 2023/10/30.
//

#ifndef OD_LEARN_CUBERENDER_H
#define OD_LEARN_CUBERENDER_H

#include "render.h"
#include "ESContext.h"


class CubeRender : public render{

public:
    void size(int w,int h);
    GLvoid draw(float greenVal);

    GLint init();
    ~CubeRender(){};

    void loadTexture(GLuint &texture_id,const char* src_name,bool useRgba);


private:
    GLuint mTexture2;

};


#endif //OD_LEARN_CUBERENDER_H
