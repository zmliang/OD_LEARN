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


void ESContext::setAssetManager(AAssetManager* &assetmanager) {
    this->mAssetManager = assetmanager;
}

char *&ESContext::readShaderSrcFromAsset(const char *srcName) {
    AAsset *pAsset = nullptr;
    char *buffer = nullptr;

    off_t size = -1;
    int numByte = -1;
    pAsset = AAssetManager_open(mAssetManager, srcName, AASSET_MODE_UNKNOWN);
    size = AAsset_getLength(pAsset);
    //ALOGV("size=%d",size);

    buffer = static_cast<char *>(malloc(size + 1));
    buffer[size] = '\0';

    numByte = AAsset_read(pAsset, buffer, size);
    ALOGV("numByte=%d",numByte);
    AAsset_close(pAsset);

    return buffer;
}