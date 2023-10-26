//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_LOG_H
#define OD_LEARN_LOG_H


#include <android/log.h>

#define LOG_TAG "ZML_Native"

#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)

#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)



#endif //OD_LEARN_LOG_H
