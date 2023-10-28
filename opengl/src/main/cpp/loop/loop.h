//
// Created by zml on 2023/10/28.
//

#ifndef OD_LEARN_LOOP_H
#define OD_LEARN_LOOP_H

#include <thread>
#include <semaphore>
#include <sys/types.h>

typedef struct LoopMessage{
    int what;
    void* object;
    LoopMessage* next;
    bool quit;
} Message;

class Looper {

public:
    Looper();
    Looper&operator=(const Looper&)=delete;
    Looper( Looper&)=delete;

    virtual ~Looper();

    void post(int what,bool flush= false);
    void post(int what,void *obj, bool flush= false);

    void quite();

    void handleMessage(Message* message);

private:
    void addMessage(Message* msg,bool flush= false);

    static void callback(Looper* p);

    void loop();

    Message *header;

    std::thread work;
    sem_t headerWriteSemt;
    sem_t headerDataAvail;
    bool running;


};


#endif //OD_LEARN_LOOP_H
