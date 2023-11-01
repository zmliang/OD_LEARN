cmake_minimum_required(VERSION 3.22.1)

set(jnilibs "${CMAKE_SOURCE_DIR}/../jniLibs")

project("gles")

#include_directories("${CMAKE_SOURCE_DIR}/inc ${CMAKE_SOURCE_DIR}/loop ${CMAKE_SOURCE_DIR}/freetype")

#add_subdirectory(glm)
#add_library(glm::glm ALIAS glm)

#直接用include_directories字目录，使用上面add_add_subdirectory+include_directories
#会报找不到第三方库的头文件
include_directories(
        inc
        loop
        freetype
        glm
)


add_library(
        gles

        SHARED

        log.h

        jni_render.cpp
        RectRender.cpp
        ESContext.cpp
        RenderLoop.cpp
        TriangleRender.cpp
        CubeRender.cpp
)


find_library(
        log-lib

        log)


add_library(freetype STATIC IMPORTED )
set_target_properties(freetype PROPERTIES IMPORTED_LOCATION ${jnilibs}/${ANDROID_ABI}/libfreetype.a)

target_link_libraries(
        gles

        ${log-lib}
        freetype
        EGL
        GLESv3
        android
)