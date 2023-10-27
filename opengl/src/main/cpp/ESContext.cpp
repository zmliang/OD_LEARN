//
// Created by 索二爷 on 2023/10/27.
//

#include "include/ESContext.h"



JavaVM* ESContext::jvm = nullptr;

JavaVM *ESContext::getJvm() {
    return jvm;
}

void ESContext::setJvm(JavaVM *javaVM) {
    jvm = javaVM;
}


render* ESContext::createRender() {
    return new ES3Render();
}
