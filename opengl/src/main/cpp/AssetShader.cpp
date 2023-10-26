//
// Created by 索二爷 on 2023/10/26.
//

#include "include/AssetShader.h"


AssetShader::AssetShader(AAssetManager *am):assetManager(am) {
    this->shaderCode = nullptr;
}

char* AssetShader::read() {
    return this->shaderCode;
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