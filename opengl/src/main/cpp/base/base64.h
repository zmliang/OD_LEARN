/****************************************************************************
 Copyright (c) 2010-2012 cocos2d-x.org
 Copyright (c) 2013-2016 Chukong Technologies Inc.
 Copyright (c) 2017-2023 Xiamen Yaji Software Co., Ltd.

 http://www.cocos.com

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
****************************************************************************/

#pragma once

#include "Macros.h"

#ifdef __cplusplus
extern "C" {
#endif

namespace cc {

/**
 * Decodes a 64base encoded memory. The decoded memory is
 * expected to be freed by the caller by calling `free()`
 *
 * @returns the length of the out buffer
 */
int base64Decode(const unsigned char *in, unsigned int inLength, unsigned char **out);

/**
 * Encodes bytes into a 64base encoded memory with terminating '\0' character.
 * The encoded memory is expected to be freed by the caller by calling `free()`
 *
 * @returns the length of the out buffer
 *
 */
int base64Encode(const unsigned char *in, unsigned int inLength, char **out);

} // namespace cc

#ifdef __cplusplus
}
#endif
