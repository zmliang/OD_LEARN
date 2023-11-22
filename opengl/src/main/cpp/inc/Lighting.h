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
#include "../inc/camera.h"

class Lighting: public render{

public:
    GLvoid draw(float greenVal);

    GLint init();

    ~Lighting(){

    }

private:

    void createShader(GLuint &program, char* vsShader, char* fsShader);

private:
    Camera camera = Camera(glm::vec3(0.0f, 0.0f, 3.0f));
    // lighting
    glm::vec3  lightPos = glm::vec3(1.2f, 1.0f, 2.0f);

    GLuint mProgramID_2;
    GLuint lightCubeVAO;

};


#endif //OD_LEARN_LIGHTING_H
