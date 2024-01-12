//
// Created by 索二爷 on 2023/12/25.
//

#ifndef OD_LEARN_MYCLASS_H
#define OD_LEARN_MYCLASS_H

#include <string>
#include <iostream>

#include "../log.h"

using namespace std;
class MyClass {
public:
    int value;

    MyClass() : value(42) {}

    void log() {
        ALOGE("这是我打印的android代码, %d",value);
        ALOGE("Hello, World!,value = %d",value );
    }

    void SetValue(int val) {
        value = val;
    }

    int GetValue() {
        return value;
    }

};


#endif //OD_LEARN_MYCLASS_H
