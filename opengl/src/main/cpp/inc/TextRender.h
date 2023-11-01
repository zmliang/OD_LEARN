//
// Created by 索二爷 on 2023/11/1.
//

#ifndef OD_LEARN_TEXTRENDER_H
#define OD_LEARN_TEXTRENDER_H

#include "render.h"
#include "ESContext.h"

class TextRender : public render{

public:
    void size(int w,int h);
    GLvoid draw(float greenVal);
    GLint init();

    ~TextRender();


};


#endif //OD_LEARN_TEXTRENDER_H
