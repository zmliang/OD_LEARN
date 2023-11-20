//
// Created by 索二爷 on 2023/10/27.
//

#include "inc/ESContext.h"



JavaVM* ESContext::jvm = nullptr;

JavaVM *ESContext::getJvm() {
    return jvm;
}

void ESContext::setJvm(JavaVM *javaVM) {
    jvm = javaVM;
}


render* ESContext::createRender() {
    return new OutBodySample();
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
    AAsset_close(pAsset);

}

void ESContext::load(const char* &srcName,unsigned char* &fileData,off_t &assetLength) {
    // 打开 Asset 文件夹下的文件
        AAsset *pathAsset = AAssetManager_open(mAssetManager, srcName, AASSET_MODE_UNKNOWN);
        // 得到文件的长度
        assetLength = AAsset_getLength(pathAsset);
        // 得到文件对应的 Buffer
        fileData = (unsigned char *) AAsset_getBuffer(pathAsset);
}

float ESContext::getDeltaTime() {
    double time = getTime();
    float delta = (float) (time - lastTime);
    //lastTime = time;
    //timeCount += delta;
    return delta;
}

float ESContext::getTime() {
    long long duration = std::chrono::steady_clock::now().time_since_epoch().count(); // steady_clock can get maching running to now duration, accuracy to 1 ns

    return (duration /1000000000.0);
}