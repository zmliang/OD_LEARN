//
// Created by zml on 2023/10/28.
//

#ifndef OD_LEARN_LOOP_H
#define OD_LEARN_LOOP_H

#include <thread>
#include <semaphore>
#include <sys/types.h>
#include <mutex>
#include <condition_variable>

#include "message.h"


template<typename T>
class Looper {

public:

    Looper(){
        this->start();
    }
    Looper&operator=(const Looper&)=delete;
    Looper( Looper&)=delete;

    virtual ~Looper(){
        if (running){
            quite();
        }
    }
    void quite(){
        running = false;
    }

    virtual void handleMessage(T message) = 0;

    void start(){
        if (running){
            return;
        }
        work = std::thread(callback, this);
        work.detach();
        running = true;
    }

    bool isRunning(){
        return running;
    }

    void postMessage(T message){
        this->postMessageDelay(message);
    }

    void postMessageDelay(T message,std::chrono::milliseconds delay = std::chrono::milliseconds(0)){
        _message.push(message,delay);
    }

    void postMessageDelay(T message,long delay){
        this->postMessageDelay(message,std::chrono::milliseconds(delay));
    }

protected:

    static void callback(Looper* p){
        p->loop();
    }

    void loop(){
        while (this->running){
            T msg;
            int status = this->_message.pop(msg);
            //ALOGE("pop message status is %d",status);
            if (status == 0){
                this->handleMessage(msg);
                continue;
            }
        }
    }

    Message<T> _message;

    std::thread work;

    bool running;



};


#endif //OD_LEARN_LOOP_H
