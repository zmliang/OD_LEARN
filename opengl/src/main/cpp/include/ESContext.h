//
// Created by 索二爷 on 2023/10/27.
//

#ifndef OD_LEARN_ESCONTEXT_H
#define OD_LEARN_ESCONTEXT_H

#include <jni.h>
#include "ES3Render.h"

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

private:
    ESContext(){};
    static JavaVM *jvm;


};


#endif //OD_LEARN_ESCONTEXT_H
