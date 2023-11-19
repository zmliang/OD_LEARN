//
// Created by zml on 2023/11/18.
//

#include "ScaleSample.h"

#include <stdlib.h>


GLint ScaleSample::init() {
    char *pVertexCode;
    ESContext::self()->readShaderSrcFromAsset("scale/vertex",pVertexCode);
    char *pFragmentCode;
    ESContext::self()->readShaderSrcFromAsset("scale/fragment",pFragmentCode);

    GLuint vertexShader = loadShader(GL_VERTEX_SHADER,pVertexCode);
    GLuint fragmentShader = loadShader(GL_FRAGMENT_SHADER,pFragmentCode);
    mProgram = glCreateProgram();

    if (mProgram == 0){
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        free(pVertexCode);
        free(pFragmentCode);
        ALOGE("create program occurred error,");
        return -1;
    }

    glAttachShader(mProgram,vertexShader);
    glAttachShader(mProgram,fragmentShader);

    glLinkProgram(mProgram);

    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
    free(pVertexCode);
    free(pFragmentCode);

    if (checkLinkStatus(mProgram)<0){
        glDeleteProgram ( mProgram );
        return -1;
    }


    float vertices[] = {
            //
            0.5f,  0.5f, 0.0f,      1.0f,1.0f,      // top right
            0.5f, -0.5f, 0.0f,      1.0f,0.0f,     // bottom right
            -0.5f, -0.5f, 0.0f,     0.0f,0.0f,    // bottom left
            -0.5f,  0.5f, 0.0f,     0.0f,1.0f      // top left
    };

    unsigned int indices[] = {  // note that we start from 0!
            0, 1, 3,  // first Triangle
            1, 2, 3   // second Triangle
    };

    glGenVertexArrays(1,&mVAO);
    glGenBuffers(1,&mVBO);
    glGenBuffers(1,&mEBO);

    glBindVertexArray(mVAO);

    //1:复制顶点数组到缓冲中，供openGL使用
    glBindBuffer(GL_ARRAY_BUFFER,mVBO);
    glBufferData(GL_ARRAY_BUFFER,sizeof(vertices),vertices,GL_STATIC_DRAW);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,mEBO);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,sizeof(indices),indices,GL_STATIC_DRAW);

    //2:三角形的坐标
    glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,5*sizeof(float),(void*)0);
    glEnableVertexAttribArray(0);

    //纹理的坐标
    glVertexAttribPointer(1,2,GL_FLOAT,GL_FALSE,5*sizeof(float),(void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);

    glBindBuffer(GL_ARRAY_BUFFER,0);//解绑
    glBindVertexArray(0);//解绑

    ALOGE("开始加载纹理");
    stbi_set_flip_vertically_on_load(true);

    loadTexture();


    glUseProgram(mProgram);
    glUniform1i(glGetUniformLocation(mProgram, "texture1"), 0);

    return 1;
}

void ScaleSample::loadTexture() {

    glGenTextures(1,&mTexture);
    glBindTexture(GL_TEXTURE_2D,mTexture);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width, height, nrChannels;

    unsigned char* fileData;
    off_t assetLength;

    const char* _name = "image/beauty.jpeg";

    ESContext::self()->load(_name,fileData,assetLength);


    // stb_image 的方法，从内存中加载图片
    unsigned char *data = stbi_load_from_memory(fileData, assetLength, &width, &height, &nrChannels, 0);
    //unsigned char *data = stbi_load(FileSystem::getPath("resources/textures/container.jpg").c_str(), &width, &height, &nrChannels, 0);

    ALOGE("图片的宽=%d， 高=%d , channel=%d",width,height,nrChannels);

    if (data){
        //让字节对齐从默认的4字节对齐改成1字节对齐（选择1的话，无论图片本身是怎样都是绝对不会出问题的，嘛，以效率的牺牲为代价）
        //将颜色数据从cpu传到gpu 叫GL_UNPACK_ALIGNMENT
        //也就是将数据从CPU端解包出来的时候的对齐准则
        //通常会用于像素传输(PACK/UNPACK)的场合。尤其是导入纹理(glTexImage2D)的时候：
        unsigned int bytesPerRow = width * (nrChannels*8) / 8;//计算一行所有像素的byte值
        int align;
        if (bytesPerRow%8 == 0){
            align = 8;
        } else if (bytesPerRow%4 == 0){
            align = 4;
        } else if(bytesPerRow % 2 == 0){
            align = 2;
        } else{
            align = 1;
        }
        glPixelStorei(GL_UNPACK_ALIGNMENT, align);

        glTexImage2D(GL_TEXTURE_2D, 0,  nrChannels == 4 ? GL_RGBA : GL_RGB, width, height, 0, nrChannels == 4 ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }else{
        ALOGE("Failed to load texture");
    }
    stbi_image_free(data);

}

GLvoid ScaleSample::draw(float greenVal) {

    // 清除颜色缓冲
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    //glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, mTexture);


    //刷新颜色
    float _timeDelta = ESContext::self()->getDeltaTime();
    float g = cos(_timeDelta);
    int colorLocation = glGetUniformLocation(mProgram,"ourColor");
    glUniform4f(colorLocation,0.0f, abs(g),1.0f,1.0f);

    int timeStampLoc = glGetUniformLocation(mProgram,"scaleConf");
    glUniform3f(timeStampLoc,_timeDelta,3,1);

    glUseProgram(mProgram);

    glBindVertexArray(mVAO);
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

}

ScaleSample::~ScaleSample(){

}


