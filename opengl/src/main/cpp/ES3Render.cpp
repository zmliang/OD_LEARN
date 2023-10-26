//
// Created by zml on 2023/10/25.
//

#include "include/ES3Render.h"



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
        return 0;
    }
    glAttachShader(mProgram,vertexShader);
    glAttachShader(mProgram,fragmentShader);

    glLinkProgram(mProgram);

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
    glClearColor ( 1.0f, 1.0f, 1.0f, 0.0f );
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


JavaVM* ES3Render::jvm = nullptr;

JavaVM *ES3Render::getJvm() {
    return jvm;
}

void ES3Render::setJvm(JavaVM *javaVM) {
    jvm = javaVM;
}


GLboolean ES3Render::createWindow(GLint width, GLint height)
{
    ALOGE("createWindow");

    return GL_TRUE;
}

GLvoid ES3Render::draw()
{
    ALOGE("ES3Render draw");
    float vertices[] = {
            0.5f,  0.5f, 0.0f,  // top right
            0.5f, -0.5f, 0.0f,  // bottom right
            -0.5f, -0.5f, 0.0f,  // bottom left

    };

    glViewport ( 0, 0, width, height );

    glClear ( GL_COLOR_BUFFER_BIT );

    glUseProgram ( mProgram );

    glVertexAttribPointer ( 0, 3, GL_FLOAT, GL_FALSE, 0, vertices );
    glEnableVertexAttribArray ( 0 );

    glDrawArrays ( GL_TRIANGLES, 0, 3 );
}


char *ES3Render::read(const char *srcName) {//"triangle/vertex"
    AAsset *pAsset = nullptr;
    char *buffer = nullptr;

    off_t size = -1;
    int numByte = -1;
    pAsset = AAssetManager_open(mAssetManager, srcName, AASSET_MODE_UNKNOWN);
    size = AAsset_getLength(pAsset);
    ALOGV("size=%d",size);

    buffer = static_cast<char *>(malloc(size + 1));
    buffer[size] = '\0';

    numByte = AAsset_read(pAsset, buffer, size);
    ALOGV("numByte=%d, content=[%s]",numByte,buffer);
    AAsset_close(pAsset);

    return buffer;
}


void ES3Render::assetManager(AAssetManager *am) {
    this->mAssetManager = am;
}


ES3Render::~ES3Render() {

}
