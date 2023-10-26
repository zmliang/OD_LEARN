//
// Created by 索二爷 on 2023/10/26.
//

#include "include/AssetShader.h"


AssetShader::AssetShader(AAssetManager *am):assetManager(am) {
    this->shaderCode = nullptr;
}

char* AssetShader::read(const char *srcName) {
    AAsset *pAsset = nullptr;
    char *buffer = nullptr;

    off_t size = -1;
    int numByte = -1;
    pAsset = AAssetManager_open(this->assetManager, srcName, AASSET_MODE_UNKNOWN);
    size = AAsset_getLength(pAsset);
    ALOGV("size=%d",size);

    buffer = static_cast<char *>(malloc(size + 1));
    buffer[size] = '\0';

    numByte = AAsset_read(pAsset, buffer, size);
    ALOGV("numByte=%d, content=[%s]",numByte,buffer);
    AAsset_close(pAsset);

    return buffer;
}


bool AssetShader::load() {
    return true;
}


bool AssetShader::load(char *path) {
    return this->load();
}


AssetShader::~AssetShader() noexcept {
//    if (assetManager){
//        delete assetManager;
//        assetManager = nullptr;
//    }

}