//
// Created by zml on 2023/11/3.
//

#include "inc/Lighting.h"
#include <iostream>



GLint Lighting::init() {
    glEnable(GL_DEPTH_TEST);
    createShader(mProgram,"lighting/1.colors.vs","lighting/1.colors.fs");
    createShader(mProgramID_2,"lighting/light_cube.vs","lighting/light_cube.fs");
    float vertices[] = {
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,

            -0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,

            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,

            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
    };

    // first, configure the cube's VAO (and mVBO)
    glGenVertexArrays(1, &mVAO);
    glGenBuffers(1, &mVBO);

    glBindBuffer(GL_ARRAY_BUFFER, mVBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    glBindVertexArray(mVAO);


    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);

    glGenVertexArrays(1, &lightCubeVAO);
    glBindVertexArray(lightCubeVAO);

    glBindBuffer(GL_ARRAY_BUFFER, mVBO);

    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);


    return 1;
}

GLvoid Lighting::draw(float greenVal) {
    // 清除颜色缓冲
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    //灯光
    glUseProgram(mProgram);
    glUniform3f(glGetUniformLocation(mProgram,"objectColor"),1.0f, 0.5f, 0.31f);
    glUniform3f(glGetUniformLocation(mProgram,"lightColor"), 1.0f, 1.0f, 1.0f);


    glm::mat4 projection = glm::perspective(glm::radians(camera.Zoom), (float)width / (float)height, 0.1f, 100.0f);
    glm::mat4 view = camera.GetViewMatrix();
    glUniformMatrix4fv(glGetUniformLocation(mProgram,"projection"),1,GL_FALSE,glm::value_ptr(projection));
    glUniformMatrix4fv(glGetUniformLocation(mProgram,"view"),1,GL_FALSE,glm::value_ptr(view));

    glm::mat4 model = glm::mat4(1.0f);
    glUniformMatrix4fv(glGetUniformLocation(mProgram,"model"),1,GL_FALSE,glm::value_ptr(model));

    glBindVertexArray(mVAO);
    glDrawArrays(GL_TRIANGLES, 0, 36);

    //====================================================

    glUseProgram(mProgramID_2);
    glUniformMatrix4fv(glGetUniformLocation(mProgramID_2,"projection"),1,GL_FALSE,glm::value_ptr(projection));
    glUniformMatrix4fv(glGetUniformLocation(mProgramID_2,"view"),1,GL_FALSE,glm::value_ptr(view));

    model = glm::mat4(1.0f);
    model = glm::translate(model, lightPos);
    model = glm::scale(model, glm::vec3(0.2f)); // a smaller cube
    glUniformMatrix4fv(glGetUniformLocation(mProgramID_2,"model"),1,GL_FALSE,glm::value_ptr(model));

    glBindVertexArray(lightCubeVAO);
    glDrawArrays(GL_TRIANGLES, 0, 36);
}

void Lighting::createShader(GLuint &program, char *vsShader, char *fsShader) {
    char *pVetCode;
    ESContext::self()->readShaderSrcFromAsset(vsShader,pVetCode);
    char *pFragCode;
    ESContext::self()->readShaderSrcFromAsset(fsShader,pFragCode);

    GLuint _vertexShader = loadShader(GL_VERTEX_SHADER,pVetCode);
    GLuint _fragmentShader = loadShader(GL_FRAGMENT_SHADER,pFragCode);
    program = glCreateProgram();

    if (program == 0){
        glDeleteShader(_vertexShader);
        glDeleteShader(_fragmentShader);
        free(pVetCode);
        free(pFragCode);
        ALOGE("create program occurred error,");
    }

    glAttachShader(program,_vertexShader);
    glAttachShader(program,_fragmentShader);
    glLinkProgram(program);

    glDeleteShader(_vertexShader);
    glDeleteShader(_fragmentShader);
    free(pVetCode);
    free(pFragCode);

    if (checkLinkStatus(program)<0){
        glDeleteProgram ( program );
    }
}

