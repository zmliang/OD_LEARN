//
// Created by zml on 2023/10/25.
//

#include "inc/RectRender.h"
#include <ctime>



GLint RectRender::init() {
    GLuint vertexShader;
    GLuint fragmentShader;
    GLint linked;

    char *pvertexShader;
    ESContext::self()->readShaderSrcFromAsset("triangle/vertex",pvertexShader);
    char *pfragmentShader;
    ESContext::self()->readShaderSrcFromAsset("triangle/fragment",pfragmentShader);

    vertexShader = loadShader(GL_VERTEX_SHADER,pvertexShader);
    fragmentShader = loadShader(GL_FRAGMENT_SHADER,pfragmentShader);

    mProgram = glCreateProgram();
    if (mProgram == 0){
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        return 0;
    }
    glAttachShader(mProgram,vertexShader);
    glAttachShader(mProgram,fragmentShader);

    glLinkProgram(mProgram);

    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
    free(pvertexShader);
    free(pfragmentShader);

    glGetProgramiv ( mProgram, GL_LINK_STATUS, &linked );

    if (!linked){
        GLint infoLen = 0;

        glGetProgramiv ( mProgram, GL_INFO_LOG_LENGTH, &infoLen );

        if ( infoLen > 1 )
        {
            char *infoLog = (char *)malloc ( sizeof ( char ) * infoLen );

            glGetProgramInfoLog ( mProgram, infoLen, NULL, infoLog );
            ALOGE("Error linking program:[%s]", infoLog );

            free ( infoLog );
        }

        glDeleteProgram ( mProgram );
        return -1;
    }

    float vertices[] = {
            0.5f,  0.5f, 0.0f,  // top right
            0.5f, -0.5f, 0.0f,  // bottom right
            -0.5f, -0.5f, 0.0f,  // bottom left
            -0.5f,  0.5f, 0.0f   // top left
    };

    unsigned int indices[] = {  // note that we start from 0!
            0, 1, 3,  // first Triangle
            1, 2, 3   // second Triangle
    };

    glGenVertexArrays(1,&mVAO);
    glGenBuffers(1,&mVBO);
    glGenBuffers(1,&mEBO);

    glBindVertexArray(mVAO);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,mEBO);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,sizeof(indices),indices,GL_STATIC_DRAW);

    //1:复制顶点数组到缓冲中，供openGL使用
    glBindBuffer(GL_ARRAY_BUFFER,mVBO);
    glBufferData(GL_ARRAY_BUFFER,sizeof(vertices),vertices,GL_STATIC_DRAW);
    //2:设置顶点属性指针，并启用
    glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,3*sizeof(float),(void*)0);
    glEnableVertexAttribArray(0);

    glBindBuffer(GL_ARRAY_BUFFER,0);//解绑
    glBindVertexArray(0);//解绑

   // glClearColor ( 1.0f, 1.0f, 1.0f, 0.0f );
    return 1;
}



//void RectRender::loop(float greenVal) {
//    //while (true){
//
//        //ALOGE("RectRender draw:%d,%d",width,height);
////    float vertices[] = {
////            0.5f,  0.5f, 0.0f,  // top right
////            0.5f, -0.5f, 0.0f,  // bottom right
////            -0.5f, -0.5f, 0.0f,  // bottom left
////
////    };
//
////    glClear ( GL_COLOR_BUFFER_BIT );
////
////    glUseProgram ( mProgram );
////
////    glVertexAttribPointer ( 0, 3, GL_FLOAT, GL_FALSE, 0, vertices );
////    glEnableVertexAttribArray ( 0 );
////
////    glDrawArrays ( GL_TRIANGLES, 0, 3 );
//
//    // 清除颜色缓冲
//    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
//    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
//
//    glUseProgram(mProgram);
//
//    //更新uniform颜色
//    //std::time_t t = std::time(0);
//    //float greenVal = sin(t)/2.0f+0.5f;
//    int colorLocation = glGetUniformLocation(mProgram,"ourColor");
//    glUniform4f(colorLocation,0.0f,greenVal,1.0f,1.0f);
//
//    glBindVertexArray(mVAO);
//    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
//
//
//
////        if (!loopFlag){
////            break;
////        }
////    }
//}

GLvoid RectRender::draw(float greenVal)
{
    // 清除颜色缓冲
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    glUseProgram(mProgram);

    //更新uniform颜色
    //std::time_t t = std::time(0);
    //float greenVal = sin(t)/2.0f+0.5f;
    int colorLocation = glGetUniformLocation(mProgram,"ourColor");
    glUniform4f(colorLocation,0.0f,greenVal,1.0f,1.0f);

    glBindVertexArray(mVAO);
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
}


RectRender::~RectRender() {

}
