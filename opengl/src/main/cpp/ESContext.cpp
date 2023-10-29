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
    return new TriangleRender();
}


void ESContext::setAssetManager(AAssetManager* &assetmanager) {
    this->mAssetManager = assetmanager;
}

void ESContext::readShaderSrcFromAsset(const char *srcName,char* &buffer) {
    AAsset *pAsset = nullptr;
    //char *buffer = nullptr;

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

}

void ESContext::loadTexture(const char* &srcName,unsigned char* &fileData,off_t &assetLength) {
    // 打开 Asset 文件夹下的文件
        AAsset *pathAsset = AAssetManager_open(mAssetManager, srcName, AASSET_MODE_UNKNOWN);
        // 得到文件的长度
        assetLength = AAsset_getLength(pathAsset);
        // 得到文件对应的 Buffer
        fileData = (unsigned char *) AAsset_getBuffer(pathAsset);
}