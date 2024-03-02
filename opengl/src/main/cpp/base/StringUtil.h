/****************************************************************************
 Copyright (c) 2021-2023 Xiamen Yaji Software Co., Ltd.

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

#include <cstdarg>
#include <cstddef>
#include "Macros.h"
#include <string>
#include <vector>

namespace cc {

class StringUtil {
public:
    static int vprintf(char *buf, const char *last, const char *fmt, va_list args);
    static int printf(char *buf, const char *last, const char *fmt, ...);
    static std::string format(const char *fmt, ...);
    static std::vector<std::string> split(const std::string &str, const std::string &delims, uint32_t maxSplits = 0);
    static std::string &replace(std::string &str, const std::string &findStr, const std::string &replaceStr);
    static std::string &replaceAll(std::string &str, const std::string &findStr, const std::string &replaceStr);
    static std::string &tolower(std::string &str);
    static std::string &toupper(std::string &str);
};

/**
 * Store compressed text which compressed with gzip & base64
 * fetch plain-text with `value()`.
 */
class  GzipedString {
public:
    explicit GzipedString(std::string &&dat) : _str(dat) {}
    explicit GzipedString(const char *dat) : _str(dat) {}
    GzipedString(const GzipedString &o) = default;
    GzipedString &operator=(const GzipedString &d) = default;

    GzipedString(GzipedString &&o) noexcept {
        _str = std::move(o._str);
    }
    GzipedString &operator=(std::string &&d) {
        _str = std::move(d);
        return *this;
    }
    GzipedString &operator=(GzipedString &&d) noexcept {
        _str = std::move(d._str);
        return *this;
    }
    /**
    * return text decompress with base64decode | un-gzip
    */
    std::string value() const;

private:
    std::string _str{};
};

} // namespace cc
