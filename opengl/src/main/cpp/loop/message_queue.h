//
// Created by 索二爷 on 2023/10/28.
//

#ifndef OD_LEARN_MESSAGE_QUEUE_H
#define OD_LEARN_MESSAGE_QUEUE_H

#include <queue>
#include <mutex>
#include <condition_variable>
#include "../log.h"
#include "message.h"




using namespace std;

template<class T>
class MessageQueue {

public:
    MessageQueue& operator = (const MessageQueue&) = delete;
    MessageQueue(const MessageQueue& mq) = delete;
    MessageQueue():mPriorityQueue(),mMutex(),conditionVariable(){}

    ~MessageQueue(){}

    void push(T msg,chrono::milliseconds delay = chrono::milliseconds(0)){
        std::unique_lock <std::mutex> lock(mMutex);
        mPriorityQueue.push(make_pair(chrono::steady_clock::now()+delay,msg));
        conditionVariable.notify_one();
    }

//    T pop(bool blocked= true){
//        std::unique_lock<std::mutex> lock(mMutex);
//
//        auto now = std::chrono::steady_clock::now(); // 获取当前时间
//        if (blocked){
//            while (mPriorityQueue.empty()) {
//                conditionVariable.wait(lock);
//            }
////            T msg = std::move(mPriorityQueue.front());
////            mPriorityQueue.pop();
////            return msg;
//
//            pair<std::chrono::time_point<std::chrono::steady_clock>, T> msg = mPriorityQueue.top();
//
//            if (now<msg.first){
//                //ALOGE("time is not arrived");
//                //conditionVariable.wait_until(lock,msg.first);
//
//                return T(-1);
//            }
//            T result;
//            mPriorityQueue.pop();
//            result = std::move(msg.second);
//            return result;
//
//        } else{
//            if (mPriorityQueue.empty()){
//                return T();
//            }
//
//            pair<std::chrono::time_point<std::chrono::steady_clock>, T> msg = mPriorityQueue.top();
//            T result;
//            if (now>=msg.first){
//                mPriorityQueue.pop();
//                result = std::move(msg.second);
//            } else{
//                //conditionVariable.wait_until(lock,msg.first);
//            }
//            return result;
//        }
//
//    }

    int pop(T& t){
        std::unique_lock<std::mutex> lock(mMutex);
        auto now = std::chrono::steady_clock::now(); // 获取当前时间
        while (mPriorityQueue.empty()) {
            conditionVariable.wait(lock);
        }
        pair<std::chrono::time_point<std::chrono::steady_clock>, T> msg = mPriorityQueue.top();

        if (now<msg.first){
            //ALOGE("time is not arrived");
            //conditionVariable.wait_until(lock,msg.first);
            return -1;
        }
        mPriorityQueue.pop();
        t = std::move(msg.second);
        return 0;
    }



    int size(){
        std::unique_lock <std::mutex> lock(mMutex);
        return mPriorityQueue.size();
    }

    bool isEmpty(){
        std::unique_lock <std::mutex> lock(mMutex);
        return mPriorityQueue.empty();
    }

private:
    mutable std::mutex mMutex;
    std::condition_variable conditionVariable;


    struct compare{
        bool operator()(const std::pair<std::chrono::time_point<std::chrono::steady_clock>, T>& lhs,
                        const std::pair<std::chrono::time_point<std::chrono::steady_clock>, T>& rhs) const
        {
            return lhs.first > rhs.first; // 比较任务的执行时间，返回较大的时间
        }
    };


    priority_queue<std::pair<chrono::time_point<chrono::steady_clock>, T>,
            vector<std::pair<chrono::time_point<chrono::steady_clock>, T>>,
            compare> mPriorityQueue;

};





#endif //OD_LEARN_MESSAGE_QUEUE_H
