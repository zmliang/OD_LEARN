//
// Created by 索二爷 on 2023/10/28.
//

#include "inc/RenderLoop.h"


RenderLoop::RenderLoop() {

}


void RenderLoop::handleMessage(int message) {
    ALOGE("Render loop handler message what is %d",message);
    this->quite();
}