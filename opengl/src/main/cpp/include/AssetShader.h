//
// Created by 索二爷 on 2023/10/26.
//

#ifndef OD_LEARN_ASSETSHADER_H
#define OD_LEARN_ASSETSHADER_H

#include <android/asset_manager_jni.h>
#include "shader.h"


class AssetShader: public shader{

public:

    AssetShader(AAssetManager *am);

    char* read(const char *srcName);

    bool load();

    bool load(char *path);

    ~AssetShader();

private:
    AAssetManager *assetManager = nullptr;

};


#endif //OD_LEARN_ASSETSHADER_H
