//
// Created by zml on 2023/10/25.
//

#ifndef OD_LEARN_ES3RENDER_H
#define OD_LEARN_ES3RENDER_H

#include "es_context.h"


class ES3Render : public es_context{


public:
    GLboolean createWindow(GLint width,GLint height);

    GLvoid draw();

    ~ES3Render();


private:
    GLint a = 0;

};


#endif //OD_LEARN_ES3RENDER_H
