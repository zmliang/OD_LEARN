//
// Created by zml on 2023/11/3.
//

#ifndef OD_LEARN_LIGHTING_H
#define OD_LEARN_LIGHTING_H

#include "render.h"
#include "ESContext.h"
#include "../glm/glm.hpp"
#include "../glm/gtc/matrix_transform.hpp"
#include "../glm/gtc/type_ptr.hpp"

class Lighting: public render{

public:
    GLvoid draw(float greenVal);

    GLint init();

    ~Lighting(){

    }

};


#endif //OD_LEARN_LIGHTING_H
