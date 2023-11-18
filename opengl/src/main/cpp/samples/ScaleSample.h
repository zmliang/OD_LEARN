//
// Created by zml on 2023/11/18.
//

#ifndef OD_LEARN_SCALESAMPLE_H
#define OD_LEARN_SCALESAMPLE_H

#include "../inc/RectRender.h"
#include <ctime>

class ScaleSample : public render{

public:
    GLvoid draw(float greenVal);

    GLint init();

    ~ScaleSample();


private:

    void loadTexture();

};


#endif //OD_LEARN_SCALESAMPLE_H
