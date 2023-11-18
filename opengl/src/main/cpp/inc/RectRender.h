//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_RECTRENDER_H
#define OD_LEARN_RECTRENDER_H

#include "render.h"
#include <atomic>
#include "ESContext.h"


class RectRender : public render{

public:
    GLvoid draw(float greenVal);
    GLint init();
    ~RectRender();

};


#endif //OD_LEARN_RECTRENDER_H
