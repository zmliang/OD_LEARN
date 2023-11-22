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
        samples
)

#file(GLOB cpp_files
#        ${CMAKE_SOURCE_DIR}/*.cpp
#        //
#       )

#查找当前目录所有源文件，并将查找到的源文件名称列表保存到变量DIR_SRCS中
aux_source_directory(${CMAKE_SOURCE_DIR}/samples/. DIR_SRCS)


add_library(
        gles
        SHARED
        log.h

        CubeRender.cpp
        ESContext.cpp
        jni_render.cpp
        RectRender.cpp
        RenderLoop.cpp
        TextRender.cpp  #文本渲染，一直没有渲染出来字体，不知道问题出在哪
        TriangleRender.cpp
        Lighting.cpp
        ${DIR_SRCS}
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