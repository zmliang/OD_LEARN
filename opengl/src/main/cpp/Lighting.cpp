//
// Created by zml on 2023/11/3.
//

#include "inc/Lighting.h"



void Lighting::size(int w, int h) {
    width = w;
    height = h;
    glViewport(0,0,w,h);
}

GLint Lighting::init() {


    return 1;
}

GLvoid Lighting::draw(float greenVal) {

}

