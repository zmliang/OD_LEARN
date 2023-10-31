cmake_minimum_required(VERSION 3.22.1)

set(jnilibs "${CMAKE_SOURCE_DIR}/../jniLibs")

project("gles")

include_directories("${PROJECT_SOURCE_DIR}/include ${PROJECT_SOURCE_DIR}/loop ${PROJECT_SOURCE_DIR}/freetype_2_10")

add_subdirectory(glm)
add_library(glm::glm ALIAS glm)

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
        freetype
        ${log-lib}
        EGL
        GLESv3
        android
)