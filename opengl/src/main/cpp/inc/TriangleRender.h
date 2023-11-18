//
// Created by 索二爷 on 2023/10/29.
//

#ifndef OD_LEARN_TRIANGLERENDER_H
#define OD_LEARN_TRIANGLERENDER_H

#include "render.h"
#include "ESContext.h"



class TriangleRender : public render{

public:
    GLvoid draw(float greenVal);

    GLint init();
    ~TriangleRender(){};

    void loadTexture(GLuint &texture_id,const char* src_name,bool useRgba);


private:
    GLuint mTexture2;

};


#endif //OD_LEARN_TRIANGLERENDER_H
