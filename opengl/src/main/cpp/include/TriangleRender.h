//
// Created by 索二爷 on 2023/10/29.
//

#ifndef OD_LEARN_TRIANGLERENDER_H
#define OD_LEARN_TRIANGLERENDER_H

#include "render.h"
#include "ESContext.h"


class TriangleRender : public render{

public:
    void size(int w,int h);
    GLvoid draw(float greenVal);

    GLint init();
    ~TriangleRender(){}

};


#endif //OD_LEARN_TRIANGLERENDER_H
