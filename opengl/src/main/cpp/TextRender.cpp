//
// Created by 索二爷 on 2023/11/1.
//

#include "inc/TextRender.h"
#include "freetype/ftglyph.h"


TextRender::TextRender() {
    if (FT_Init_FreeType(&ft)){
        ALOGE("ERROR::FREETYPE: Could not init FreeType Library");
    }

    const char *font_file = "font/Antonio-Bold.ttf";
    unsigned char* buffer;
    off_t assetLength;

    ESContext::self()->load(font_file,buffer,assetLength);

    if (FT_New_Memory_Face(ft, buffer, assetLength,0, &face)){
        ALOGE("ERROR::FREETYPE: Failed to load font");
    }



}

void TextRender::mapCharacter() {

    //设置字体大小，TODO 宽度值设为0表示我们要从字体面通过给定的高度中动态计算出字形的宽度
// Set size to load glyphs as
    FT_Set_Pixel_Sizes(face, 0, 48);
    FT_Select_Charmap(face, ft_encoding_unicode);
    // Disable byte-alignment restriction
    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    for (GLubyte c = 0; c < 128; c++) {


        //int index =  FT_Get_Char_Index(face,unicodeArr[i]);
        if (FT_Load_Glyph(face, FT_Get_Char_Index(face, c), FT_LOAD_DEFAULT)) {
            ALOGE("Failed to load Glyph");
            continue;
        }

        FT_Glyph glyph;
        FT_Get_Glyph(face->glyph, &glyph);

        //Convert the glyph to a bitmap.
        FT_Glyph_To_Bitmap(&glyph, ft_render_mode_normal, 0, 1);
        FT_BitmapGlyph bitmap_glyph = (FT_BitmapGlyph) glyph;

        //This reference will make accessing the bitmap easier
        FT_Bitmap &bitmap = bitmap_glyph->bitmap;



//        if (FT_Load_Char(face, c, FT_LOAD_DEFAULT)){
//            ALOGE("ERROR::FREETYTPE: Failed to load Glyph");
//            continue;
//        }


        GLuint texture;
        glGenTextures(1,&texture);
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RED,
                bitmap.width,
                bitmap.rows,
                0,
                GL_RED,
                GL_UNSIGNED_BYTE,
                bitmap.buffer
        );

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        CHARACTER _c = {
                texture,
                glm::ivec2(bitmap.width, bitmap.rows),
                glm::ivec2(face->glyph->bitmap_left, face->glyph->bitmap_top),
                static_cast<GLuint>((face->glyph->advance.x ) )
        };
        mCharacters.insert(std::pair<GLchar, CHARACTER>(c, _c));
    }
    glBindTexture(GL_TEXTURE_2D, 0);
    FT_Done_Face(face);
    FT_Done_FreeType(ft);
}


GLint TextRender::init() {
    ALOGE("onInit =  width=%d, height=%d",width,height);
//    // Set OpenGL options
//    //glEnable(GL_CULL_FACE);
//    glEnable(GL_BLEND);
//    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


    glEnable(GL_BLEND);
    glEnable(GL_CULL_FACE);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    //glDisable(GL_DEPTH_TEST);

    GLint linked;

    char *pvertexShader;
    ESContext::self()->readShaderSrcFromAsset("text/vertex",pvertexShader);
    char *pfragmentShader;
    ESContext::self()->readShaderSrcFromAsset("text/fragment",pfragmentShader);
    GLuint vertexShader = loadShader(GL_VERTEX_SHADER,pvertexShader);
    GLuint fragmentShader = loadShader(GL_FRAGMENT_SHADER,pfragmentShader);

    mProgram = glCreateProgram();
    if (mProgram == 0){
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        return 0;
    }
    glAttachShader(mProgram,vertexShader);
    glAttachShader(mProgram,fragmentShader);

    glLinkProgram(mProgram);

//    _textureLoc  = glGetUniformLocation(mProgram, "text");

   // glDeleteShader(vertexShader);
   // glDeleteShader(fragmentShader);
   // free(pvertexShader);
   // free(pfragmentShader);

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

//    if (FT_Init_FreeType(&ft)){
//        ALOGE("ERROR::FREETYPE: Could not init FreeType Library");
//    }
//
//    const char *font_file = "font/jianti.ttf";
//    unsigned char* buffer;
//    off_t assetLength;
//
//    ESContext::self()->load(font_file,buffer,assetLength);
//
//    if (FT_New_Memory_Face(ft, buffer, assetLength,0, &face)){
//        ALOGE("ERROR::FREETYPE: Failed to load font");
//        return -1;
//    }

//    //设置字体大小，TODO 宽度值设为0表示我们要从字体面通过给定的高度中动态计算出字形的宽度
//    FT_Set_Pixel_Sizes(face, 0, 48);
//    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    mapCharacter();

    glGenVertexArrays(1, &mVAO);
    glGenBuffers(1, &mVBO);
    glBindVertexArray(mVAO);
    glBindBuffer(GL_ARRAY_BUFFER, mVBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * 6 * 4, NULL, GL_DYNAMIC_DRAW);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 4, GL_FLOAT, GL_FALSE, 4 * sizeof(GLfloat), 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    //glUseProgram(mProgram);
    //glUniform1i(glGetUniformLocation(mProgram, "text"), 0);


    return 1;
}

GLvoid TextRender::draw(float greenVal)
{
    // 清除颜色缓冲
    // Clear the colorbuffer
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glm::mat4 projection = glm::ortho(0.0f, static_cast<GLfloat>(width), 0.0f, static_cast<GLfloat>(height));
    //glUseProgram(mProgram);
    glUniformMatrix4fv(glGetUniformLocation(mProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

    glUseProgram(mProgram);
    doRender("This is program",20.0f, 570.0f, 2.0f, glm::vec3(0.8, 0.1f, 0.1f));

//    int colorLocation = glGetUniformLocation(mProgram,"ourColor");
//    glUniform4f(colorLocation,0.0f,0.5f,1.0f,1.0f);
//
//    glBindVertexArray(mVAO);
//    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
}

void TextRender::doRender(const std::string txt, GLfloat x, GLfloat y, GLfloat scale, glm::vec3 color) {
    glUniform3f(glGetUniformLocation(mProgram, "textColor"), color.x, color.y, color.z);
    glActiveTexture(GL_TEXTURE0);
    glBindVertexArray(mVAO);

    //x *= width;
    //y *= height;

    std::string::const_iterator c;
    for (c = txt.begin(); c != txt.end(); c++) {

        CHARACTER character = mCharacters[*c];

        GLfloat xpos = x + character.bearing.x * scale;
        GLfloat ypos = y - (character.size.y - character.bearing.y) * scale;

        //xpos /= width;
        //ypos /= height;

        GLfloat w = character.size.x * scale;
        GLfloat h = character.size.y * scale;

       // w /= width;
       // h /= height;

/*
        float xpos = x + character.bearing.x * scale;
        float ypos = y - (character.size.y - character.bearing.y) * scale;

        float w = character.size.x * scale;
        float h = character.size.y * scale;
*/
        //ALOGE("XPOS = %f; YPOS=%f; width=%f; height=%f",xpos,ypos,w,h);

        GLfloat vertices[6][4] = {
                {xpos,     ypos + h, 0.0, 0.0},
                {xpos,     ypos,     0.0, 1.0},
                {xpos + w, ypos,     1.0, 1.0},

                {xpos,     ypos + h, 0.0, 0.0},
                {xpos + w, ypos,     1.0, 1.0},
                {xpos + w, ypos + h, 1.0, 0.0}

        };

        //绑定纹理图片
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, character._textureId);

        glBindBuffer(GL_ARRAY_BUFFER, mVBO);
        glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(vertices), vertices);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDrawArrays(GL_TRIANGLES, 0, 6);

        x += (character.advance >> 6) * scale; // Bitshift
    }
    glBindVertexArray(0);
    glBindTexture(GL_TEXTURE_2D, 0);
}


TextRender::~TextRender() {

}