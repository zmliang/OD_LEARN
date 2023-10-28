//
// Created by 索二爷 on 2023/10/28.
//

#include "include/RenderLoop.h"


RenderLoop::RenderLoop() {

}


void RenderLoop::handleMessage(int message) {
    ALOGV("Render loop handler message is what=%d",message);
    this->quite();
}