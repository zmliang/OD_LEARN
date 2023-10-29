//
// Created by 索二爷 on 2023/10/27.
//

#ifndef OD_LEARN_ESCONTEXT_H
#define OD_LEARN_ESCONTEXT_H

#include <jni.h>
#include "RectRender.h"
#include "TriangleRender.h"

class ESContext {

public:

    render* createRender();


public:
    static ESContext* self()//线程安全单例
    {
        static ESContext instance;
        return &instance;
    }


    static void setJvm(JavaVM *javaVM);
    static JavaVM* getJvm();

    void setAssetManager(AAssetManager* &assetmanager);

    void loadTexture(const char* &srcName,unsigned char* &fileData,off_t &assetLength);

    void readShaderSrcFromAsset(const char *srcName,char* &buffer);

    float getDeltaTime();

    float getTime();

private:
    ESContext(){
        lastTime = getTime();
    };
    static JavaVM *jvm;

    double lastTime ;

    AAssetManager *mAssetManager = nullptr;


};


#endif //OD_LEARN_ESCONTEXT_H
