//
// Created by 索二爷 on 2023/11/1.
//

#ifndef OD_LEARN_TEXTRENDER_H
#define OD_LEARN_TEXTRENDER_H

#include "render.h"
#include "ESContext.h"

extern "C"{
#include <ft2build.h>
#include FT_FREETYPE_H
}


class TextRender : public render{

public:

    TextRender();

    void size(int w,int h);
    GLvoid draw(float greenVal);
    GLint init();

    ~TextRender();

private:
    FT_Library ft;
    FT_Face face;

};


#endif //OD_LEARN_TEXTRENDER_H
