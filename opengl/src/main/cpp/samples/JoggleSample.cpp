//
// Created by zml on 2023/11/21.
//

#include "JoggleSample.h"

GLint JoggleSample::init() {
    char *pVetCode;
    ESContext::self()->readShaderSrcFromAsset("joggle/vertex",pVetCode);
    char *pFragCode;
    ESContext::self()->readShaderSrcFromAsset("joggle/fragment",pFragCode);

    GLuint _vertexShader = loadShader(GL_VERTEX_SHADER,pVetCode);
    GLuint _fragmentShader = loadShader(GL_FRAGMENT_SHADER,pFragCode);
    mProgram = glCreateProgram();

    if (mProgram == 0){
        glDeleteShader(_vertexShader);
        glDeleteShader(_fragmentShader);
        free(pVetCode);
        free(pFragCode);
        ALOGE("create program occurred error,");
        return -1;
    }

    glAttachShader(mProgram,_vertexShader);
    glAttachShader(mProgram,_fragmentShader);
    glLinkProgram(mProgram);

    glDeleteShader(_vertexShader);
    glDeleteShader(_fragmentShader);
    free(pVetCode);
    free(pFragCode);

    if (checkLinkStatus(mProgram)<0){
        glDeleteProgram ( mProgram );
        return -1;
    }

    float vertices[] = {
            //
            0.9f,  0.9f, 0.0f,      1.0f,1.0f,      // top right
            0.9f, -0.9f, 0.0f,      1.0f,0.0f,     // bottom right
            -0.9f, -0.9f, 0.0f,     0.0f,0.0f,    // bottom left
            -0.9f,  0.9f, 0.0f,     0.0f,1.0f      // top left
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

    glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,5*sizeof(GL_FLOAT),(void*)0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1,2,GL_FLOAT,GL_FALSE,5*sizeof(GL_FLOAT),(void*)(3*sizeof(GL_FLOAT)));
    glEnableVertexAttribArray(1);

    glBindBuffer(GL_ARRAY_BUFFER,0);//解绑
    glBindVertexArray(0);//解绑

    stbi_set_flip_vertically_on_load(true);

    loadTexture(mTexture,"image/beauty.jpeg");

    glUseProgram(mProgram);
    glUniform1i(glGetUniformLocation(mProgram, "_texture"), 0);

    return 1;

}

void JoggleSample::loadTexture( GLuint &textureId, const char *textureSource) {
    glGenTextures(1,&textureId);
    glBindTexture(GL_TEXTURE_2D,textureId);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width, height, nrChannels;

    unsigned char* fileData;
    off_t assetLength;

    ESContext::self()->load(textureSource,fileData,assetLength);


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

GLvoid JoggleSample::draw(float greenVal) {
    // 清除颜色缓冲
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    glBindTexture(GL_TEXTURE_2D, mTexture);

    glUniform1f(glGetUniformLocation(mProgram,"_time"),ESContext::self()->getDeltaTime());

    glUseProgram(mProgram);

    glBindVertexArray(mVAO);
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
}

JoggleSample::~JoggleSample() noexcept {

}