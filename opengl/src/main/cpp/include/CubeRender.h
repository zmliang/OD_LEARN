//
// Created by zml on 2023/10/30.
//

#ifndef OD_LEARN_CUBERENDER_H
#define OD_LEARN_CUBERENDER_H

#include "render.h"
#include "ESContext.h"
#include "../glm/glm.hpp"
#include "../glm/gtc/matrix_transform.hpp"
#include "../glm/gtc/type_ptr.hpp"


#include "../freetype/ft2build.h"
#include FT_FREETYPE_H


class CubeRender : public render{

public:
    void size(int w,int h);
    GLvoid draw(float greenVal);

    GLint init();
    ~CubeRender(){};

    void loadTexture(GLuint &texture_id,const char* src_name,bool useRgba);


private:

    //摄像机的位置，随时间转动
    void cameraPos(glm::mat4 &view);

    void drawChildCube();

    GLuint mTexture2;

};


#endif //OD_LEARN_CUBERENDER_H
