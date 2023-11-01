//
// Created by zml on 2023/10/30.
//

#include "inc/CubeRender.h"


void CubeRender::size(int w, int h) {
    this->width = w;
    this->height = h;

    glViewport ( 0, 0, width, height );
}

GLint CubeRender::init() {
    glEnable(GL_DEPTH_TEST);
    GLuint vertexShader;
    GLuint fragmentShader;
    GLint linked;

    char *pvertexShader;
    ESContext::self()->readShaderSrcFromAsset("cube/vertex",pvertexShader);
    char *pfragmentShader;
    ESContext::self()->readShaderSrcFromAsset("cube/fragment",pfragmentShader);

    vertexShader = loadShader(GL_VERTEX_SHADER,pvertexShader);
    fragmentShader = loadShader(GL_FRAGMENT_SHADER,pfragmentShader);

    mProgram = glCreateProgram();
    if (mProgram == 0){
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        free(pvertexShader);
        free(pfragmentShader);
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

//    float vertices[] = {
//            // positions          // colors           // texture coords
//            0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f, // top right
//            0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f, // bottom right
//            -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f, // bottom left
//            -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f  // top left
//    };

//当使用glDrawElements进行绘制时，需要使用顶点位置数据，
//而使用glDrawArrays时，不需要这个
    unsigned int indices[] = {  // note that we start from 0!
            0, 1, 3,  // first Triangle
            1, 2, 3   // second Triangle
    };

    float vertices[] = {
            //顶点                                  //颜色                          //纹理坐标
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
            0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,
            0.5f,  0.5f, -0.5f,   0.0f, 0.0f, 1.0f,   1.0f, 1.0f,
            0.5f,  0.5f, -0.5f,   1.0f, 1.0f, 0.0f,   1.0f, 1.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f,   0.0f, 0.0f,

            -0.5f, -0.5f,  0.5f, 0.0f, 1.0f, 0.0f,    0.0f, 0.0f,
            0.5f, -0.5f,  0.5f,  0.0f, 1.0f, 0.0f,    1.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 0.0f,    1.0f, 1.0f,
            0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 0.0f,    1.0f, 1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 0.0f,   0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 1.0f, 0.0f,   0.0f, 0.0f,

            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 0.0f,

            0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,    0.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,    0.0f, 1.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    0.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,    0.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 1.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,
            -0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f,

            -0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 1.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,    1.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   0.0f, 1.0f
    };

    glGenVertexArrays(1,&mVAO);
    glGenBuffers(1,&mVBO);
    //glGenBuffers(1,&mEBO);

    glBindVertexArray(mVAO);

    //1:复制顶点数组到缓冲中，供openGL使用
    glBindBuffer(GL_ARRAY_BUFFER,mVBO);
    glBufferData(GL_ARRAY_BUFFER,sizeof(vertices),vertices,GL_STATIC_DRAW);

    //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mEBO);
    //glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);


    //位置 属性
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);

    //颜色 属性
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);

    //纹理 属性
    glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
    glEnableVertexAttribArray(2);

    glBindBuffer(GL_ARRAY_BUFFER,0);//解绑
    glBindVertexArray(0);//解绑


    //load texture....
    stbi_set_flip_vertically_on_load(true);
    loadTexture(mTexture,"image/container.jpg", false);

    loadTexture(mTexture2,"image/awesomeface.png", true);


    // tell opengl for each sampler to which texture unit it belongs to (only has to be done once)
    // -------------------------------------------------------------------------------------------
    glUseProgram(mProgram); // don't forget to activate/use the shader before setting uniforms!
    // either set it manually like so:
    glUniform1i(glGetUniformLocation(mProgram, "texture1"), 0);
    // or set it via the texture class
    glUniform1i(glGetUniformLocation(mProgram, "texture2"), 1);


    //透视投影，就是使物体有远近大小的效果
    //创建了一个定义了可视空间的大平截头体
    //第一个参数fov它表示的是视野(Field of View)，并且设置了观察空间的大小
    //第二个参数设置了宽高比，由视口的宽除以高所得
    //第三和第四个参数设置了平截头体的近和远平面。我们通常设置近距离为0.1f，而远距离设为100.0f

    return 1;
}

GLvoid CubeRender::draw(float greenVal) {
    // 清除颜色缓冲
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    // 绑定纹理
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, mTexture);
    glActiveTexture(GL_TEXTURE1);
    glBindTexture(GL_TEXTURE_2D, mTexture2);


    glUseProgram(mProgram);

    //矩阵 开始
    //glm::mat4 model = glm::mat4(1.0f);//模型矩阵
    glm::mat4 view = glm::mat4(1.0f);//观察矩阵
    glm::mat4 projection = glm::mat4(1.0f);//透视矩阵

    //model = glm::rotate(model, glm::radians(-55.0f), glm::vec3(1.0f, 0.0f, 0.0f));
    view  = glm::translate(view, glm::vec3(0.0f, 0.0f, -3.0f));
    projection = glm::perspective(glm::radians(70.0f), (float)width / (float)height, 0.1f, 100.0f);

//    int modelLoc = glGetUniformLocation(mProgram, "model");
//    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));
    this->cameraPos(view);

    int viewLoc = glGetUniformLocation(mProgram, "view");
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

    int projLoc = glGetUniformLocation(mProgram, "projection");
    glUniformMatrix4fv(projLoc, 1, GL_FALSE, &projection[0][0]);
    //矩阵 结束

    glm::mat4 trans = glm::mat4(1.0f);//glm使用的版本是9.8，初始化的时候需要显示指定单位矩阵
    trans = glm::rotate(trans, ESContext::self()->getDeltaTime(), glm::vec3(0.0, 0.0, 1.0));
    glUniformMatrix4fv( glGetUniformLocation(mProgram, "transform"), 1, GL_FALSE, glm::value_ptr(trans));



    //==================

    glBindVertexArray(mVAO);

    this->drawChildCube();

    //glBindVertexArray(mVAO);
    //glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0);
   // glDrawArrays(GL_TRIANGLES, 0, 36);

}

void CubeRender::loadTexture(GLuint &texture_id,const char* src_name,bool useRgba) {
    glGenTextures(1,&texture_id);
    glBindTexture(GL_TEXTURE_2D,texture_id);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	// set texture wrapping to GL_REPEAT (default wrapping method)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    // set texture filtering parameters
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    // load image, create texture and generate mipmaps
    int width, height, nrChannels;

    unsigned char* fileData;
    off_t assetLength;

    ESContext::self()->loadTexture(src_name,fileData,assetLength);

    // stb_image 的方法，从内存中加载图片
    unsigned char *data = stbi_load_from_memory(fileData, assetLength, &width, &height, &nrChannels, 0);
    //unsigned char *data = stbi_load(FileSystem::getPath("resources/textures/container.jpg").c_str(), &width, &height, &nrChannels, 0);
    if (data){
        glTexImage2D(GL_TEXTURE_2D, 0, useRgba ? GL_RGBA : GL_RGB, width, height, 0, useRgba ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }else{
        ALOGE("Failed to load texture");
    }
    stbi_image_free(data);

}

void CubeRender::cameraPos(glm::mat4 &view) {
    glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, 3.0f);
    glm::vec3 cameraTarget = glm::vec3(0.0f, 0.0f, 0.0f);
    glm::vec3 cameraDirection = glm::normalize(cameraPos - cameraTarget);//两个向量减，就是方向

    glm::vec3 up = glm::vec3(0.0f, 1.0f, 0.0f);
    glm::vec3 cameraRight = glm::normalize(glm::cross(up, cameraDirection));

    glm::vec3 cameraUp = glm::cross(cameraDirection, cameraRight);

    float radius = 10.0f;
    float camX = sin(ESContext::self()->getDeltaTime()) * radius;
    float camZ = cos(ESContext::self()->getDeltaTime()) * radius;
    view = glm::lookAt(glm::vec3(camX, 0.0, camZ), glm::vec3(0.0, 0.0, 0.0), glm::vec3(0.0, 1.0, 0.0));
}

void CubeRender::drawChildCube() {
    glm::vec3 cubePositions[] = {
            glm::vec3( 0.0f,  0.0f,  0.0f),
            glm::vec3( 2.0f,  5.0f, -15.0f),
            glm::vec3(-1.5f, -2.2f, -2.5f),
            glm::vec3(-3.8f, -2.0f, -12.3f),
            glm::vec3( 2.4f, -0.4f, -3.5f),
            glm::vec3(-1.7f,  3.0f, -7.5f),
            glm::vec3( 1.3f, -2.0f, -2.5f),
            glm::vec3( 1.5f,  2.0f, -2.5f),
            glm::vec3( 1.5f,  0.2f, -1.5f),
            glm::vec3(-1.3f,  1.0f, -1.5f)
    };
    for(unsigned int i = 0; i < 10; i++)
    {
        glm::mat4 model=glm::mat4(1.0f);
        model = glm::translate(model, cubePositions[i]);
        float angle = 20.0f * i;
        model = glm::rotate(model, glm::radians(angle), glm::vec3(1.0f, 0.3f, 0.5f));

        int modelLoc = glGetUniformLocation(mProgram, "model");
        glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

        glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}
