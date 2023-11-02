//
// Created by 索二爷 on 2023/11/1.
//

#ifndef OD_LEARN_TEXTRENDER_H
#define OD_LEARN_TEXTRENDER_H

#include "render.h"
#include "ESContext.h"
#include <map>
#include <string>
#include <iostream>
#include "../glm/glm.hpp"
#include "../glm/gtc/matrix_transform.hpp"
#include "../glm/gtc/type_ptr.hpp"

extern "C"{
#include <ft2build.h>
#include FT_FREETYPE_H
}


typedef struct{
    GLuint _textureId;
    glm::ivec2 size;//字符的宽高
    glm::ivec2 bearing;//距离基准线左边/上边的距离
    GLuint advance;//水平方向上，两个字符原点之间的距离
} CHARACTER;

class TextRender : public render{

public:

    TextRender();

    void size(int w,int h);
    GLvoid draw(float greenVal);
    GLint init();

    ~TextRender();

private:
    void mapCharacter();

    void doRender(const std::string txt,GLfloat x,GLfloat y,GLfloat scale,glm::vec3 color);

private:


    FT_Library ft;
    FT_Face face;
    std::map<GLchar,CHARACTER> mCharacters;
};


#endif //OD_LEARN_TEXTRENDER_H
