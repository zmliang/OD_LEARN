//
// Created by 索二爷 on 2023/10/28.
//

#ifndef OD_LEARN_RENDERLOOP_H
#define OD_LEARN_RENDERLOOP_H
#include "../loop/loop.h"

#include "../log.h"

class RenderLoop :public Looper<int>{

public:

    RenderLoop();

    void handleMessage(int message) override;

    ~RenderLoop(){}
};


#endif //OD_LEARN_RENDERLOOP_H
