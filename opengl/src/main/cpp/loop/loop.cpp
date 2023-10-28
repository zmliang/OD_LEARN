//
// Created by zml on 2023/10/28.
//

#include "loop.h"

void Looper::callback(Looper *p) {
    p->loop();
}


Looper::Looper() {


}
