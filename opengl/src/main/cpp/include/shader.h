//
// Created by 索二爷 on 2023/10/26.
//

#ifndef OD_LEARN_SHADER_H
#define OD_LEARN_SHADER_H

#include "../log.h"
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>

class shader {
public:

    virtual char* read(const char *srcName) = 0;

    virtual bool load() = 0;

    virtual bool load(char *path) = 0;

    virtual ~shader(){
        this->shaderCode = nullptr;
    }

protected:
    char *shaderCode;
};


#endif //OD_LEARN_SHADER_H
