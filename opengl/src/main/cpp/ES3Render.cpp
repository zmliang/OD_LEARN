//
// Created by zml on 2023/10/25.
//

#include "include/ES3Render.h"
#include <ctime>



GLint ES3Render::init() {
    GLuint vertexShader;
    GLuint fragmentShader;
    GLint linked;

    char *pvertexShader = read("triangle/vertex");
    char *pfragmentShader = read("triangle/fragment");
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

    unsigned int  mEBO;

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

void ES3Render::size(int w, int h) {
    this->width = w;
    this->height = h;
}

GLuint ES3Render::loadShader(const GLenum type, const char *shaderSrc) {
    GLuint shader;
    GLint compiler;

    shader = glCreateShader(type);
    if (shader == 0){
        return 0;
    }

    glShaderSource(shader,1,&shaderSrc,NULL);

    glCompileShader(shader);

    glGetShaderiv(shader,GL_COMPILE_STATUS,&compiler);
    if (!compiler){
        GLint logLen;
        glGetShaderiv(shader,GL_INFO_LOG_LENGTH,&logLen);
        if ( logLen > 1 )
        {
            char *infoLog = (char *)malloc ( sizeof ( char ) * logLen );
            glGetShaderInfoLog ( shader, logLen, NULL, infoLog );
            ALOGE("Error compiling shader:[%s]", infoLog );

            free ( infoLog );
        }

        glDeleteShader(shader);
        return 0;
    }

    return shader;

}

void ES3Render::loop() {
    while (true){

        ALOGE("ES3Render draw:%d,%d",width,height);
//    float vertices[] = {
//            0.5f,  0.5f, 0.0f,  // top right
//            0.5f, -0.5f, 0.0f,  // bottom right
//            -0.5f, -0.5f, 0.0f,  // bottom left
//
//    };

        glViewport ( 0, 0, width, height );

//    glClear ( GL_COLOR_BUFFER_BIT );
//
//    glUseProgram ( mProgram );
//
//    glVertexAttribPointer ( 0, 3, GL_FLOAT, GL_FALSE, 0, vertices );
//    glEnableVertexAttribArray ( 0 );
//
//    glDrawArrays ( GL_TRIANGLES, 0, 3 );

        glClearColor(0.4f,0.2f,0.2f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(mProgram);

        //更新uniform颜色
        std::time_t t = std::time(0);
        float greenVal = sin(t)/2.0f+0.5f;
        int colorLocation = glGetUniformLocation(mProgram,"ourColor");
        glUniform4f(colorLocation,0.0f,greenVal,1.0f,1.0f);

        glBindVertexArray(mVAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        if (!loopFlag){
            break;
        }
    }
}

GLvoid ES3Render::draw()
{
   loop();
}


char *ES3Render::read(const char *srcName) {//"triangle/vertex"
    AAsset *pAsset = nullptr;
    char *buffer = nullptr;

    off_t size = -1;
    int numByte = -1;
    pAsset = AAssetManager_open(mAssetManager, srcName, AASSET_MODE_UNKNOWN);
    size = AAsset_getLength(pAsset);
    //ALOGV("size=%d",size);

    buffer = static_cast<char *>(malloc(size + 1));
    buffer[size] = '\0';

    numByte = AAsset_read(pAsset, buffer, size);
    //ALOGV("numByte=%d, content=[%s]",numByte,buffer);
    AAsset_close(pAsset);

    return buffer;
}


void ES3Render::assetManager(AAssetManager *am) {
    this->mAssetManager = am;
}


ES3Render::~ES3Render() {

}
