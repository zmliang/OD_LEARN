//
// Created by 索二爷 on 2023/12/21.
//
#include <jni.h>
#include <string>

#include "include/libplatform/libplatform.h"
#include "include/v8.h"
#include "log.h"
#include "v8test/MyClass.h"


using namespace v8;

extern "C"
JNIEXPORT void JNICALL
Java_com_zml_v8_v8native_runScript__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(
        JNIEnv *env, jobject thiz, jstring code, jstring func, jstring url) {
    ALOGE("进入了这个方法");
    std::unique_ptr<v8::Platform> platform = v8::platform::NewDefaultPlatform();
    v8::V8::InitializePlatform(platform.get());
    v8::V8::Initialize();


    v8::Isolate::CreateParams create_params;
    create_params.array_buffer_allocator =
            v8::ArrayBuffer::Allocator::NewDefaultAllocator();
    v8::Isolate* isolate = v8::Isolate::New(create_params);

    {
        v8::Isolate::Scope isolate_scope(isolate);

        v8::HandleScope handle_scope(isolate);
// Create a new context.
        v8::Local<v8::Context> context = v8::Context::New(isolate);


        {
// Create a string containing the JavaScript source code.
            v8::Local<v8::String> source =
                    v8::String::NewFromUtf8Literal(isolate, "'Hello' + ', World!'");
// Compile the source code.
            v8::Local<v8::Script> script =
                    v8::Script::Compile(context, source).ToLocalChecked();
// Run the script to get the result.
            v8::Local<v8::Value> result = script->Run(context).ToLocalChecked();
// Convert the result to an UTF8 string and print it.
            v8::String::Utf8Value utf8(isolate, result);
            ALOGE("%s\n", *utf8);
        }
        {
            const char csource[] = R"(
        let bytes = new Uint8Array([
          0x00, 0x61, 0x73, 0x6d, 0x01, 0x00, 0x00, 0x00, 0x01, 0x07, 0x01,
          0x60, 0x02, 0x7f, 0x7f, 0x01, 0x7f, 0x03, 0x02, 0x01, 0x00, 0x07,
          0x07, 0x01, 0x03, 0x61, 0x64, 0x64, 0x00, 0x00, 0x0a, 0x09, 0x01,
          0x07, 0x00, 0x20, 0x00, 0x20, 0x01, 0x6a, 0x0b
        ]);
        let module = new WebAssembly.Module(bytes);
        let instance = new WebAssembly.Instance(module);
        instance.exports.add(3, 4);
      )";
// Create a string containing the JavaScript source code.
            v8::Local<v8::String> source =
                    v8::String::NewFromUtf8Literal(isolate, csource);
// Compile the source code.
            v8::Local<v8::Script> script =
                    v8::Script::Compile(context, source).ToLocalChecked();
// Run the script to get the result.
            v8::Local<v8::Value> result = script->Run(context).ToLocalChecked();
// Convert the result to a uint32 and print it.
            uint32_t number = result->Uint32Value(context).ToChecked();
            ALOGE("3 + 4 = %u\n", number);
        }
    }
    isolate->Dispose();
    v8::V8::Dispose();
    v8::V8::ShutdownPlatform();
    delete create_params.array_buffer_allocator;
}



// 静态成员函数，用于绑定到 JavaScript 以支持构造函数
static void New(const v8::FunctionCallbackInfo<v8::Value>& args) {
    v8::Isolate* isolate = args.GetIsolate();
    //v8::HandleScope scope(isolate);

    if (args.IsConstructCall()) {
        MyClass* obj = new MyClass();
        args.This()->SetAlignedPointerInInternalField(0,obj);
    } else {
        isolate->ThrowException(v8::Exception::TypeError(
                v8::String::NewFromUtf8(isolate, "Use 'new' keyword to create instances of MyClass").ToLocalChecked()));
    }
}

static void HelloWorld(const v8::FunctionCallbackInfo<v8::Value>& args) {
    MyClass* obj = reinterpret_cast<MyClass*>(args.This()->GetAlignedPointerFromInternalField(0));
    obj->log();
}

static void SetValue(const v8::FunctionCallbackInfo<v8::Value>& args) {
    MyClass* obj = reinterpret_cast<MyClass*>(args.This()->GetAlignedPointerFromInternalField(0));
    if (args.Length() == 1 && args[0]->IsNumber()) {
        int val = args[0].As<v8::Number>()->Value();
        obj->SetValue(val);
    }
}

static void GetValue(const v8::FunctionCallbackInfo<v8::Value>& args) {
    MyClass* obj = reinterpret_cast<MyClass*>(args.This()->GetAlignedPointerFromInternalField(0));
    args.GetReturnValue().Set(v8::Number::New(args.GetIsolate(), obj->GetValue()));
}

static void JSRegister(const v8::Local<v8::Object> exports){
    v8::Isolate* isolate = exports->GetIsolate();
    v8::Local<v8::Context> context = isolate->GetCurrentContext();

    //生成构造函数模版对象，并设置类名
    Local<FunctionTemplate> function_t = FunctionTemplate::New(isolate,New);
    function_t->SetClassName(String::NewFromUtf8(isolate,"MyClass").ToLocalChecked());

    //生成构造函数对应的对象模版，并设置一个槽位，用于存放与C++对象关联的指针数据
    Local<ObjectTemplate> instance_t = function_t->InstanceTemplate();
    instance_t->SetInternalFieldCount(1);

    //在对应的js原型对象上设置方法
    function_t->PrototypeTemplate()->Set(isolate,"log",FunctionTemplate::New(isolate,HelloWorld));

    function_t->PrototypeTemplate()->Set(isolate,"SetValue",FunctionTemplate::New(isolate,SetValue));

    function_t->PrototypeTemplate()->Set(isolate,"GetValue",FunctionTemplate::New(isolate,GetValue));

    //生成构造方法
    Local<Function> function = function_t->GetFunction(context).ToLocalChecked();
    exports->Set(context,String::NewFromUtf8(isolate,"MyClass").ToLocalChecked(),function);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_zml_v8_v8native_obj(JNIEnv *env, jobject thiz) {
    std::unique_ptr<v8::Platform> platform = v8::platform::NewDefaultPlatform();
    v8::V8::InitializePlatform(platform.get());
    v8::V8::Initialize();


    v8::Isolate::CreateParams create_params;
    create_params.array_buffer_allocator =
            v8::ArrayBuffer::Allocator::NewDefaultAllocator();
    v8::Isolate* isolate = v8::Isolate::New(create_params);

    {
        // 创建一个上下文
        v8::HandleScope handle_scope(isolate);
        v8::Local<v8::Context> context = v8::Context::New(isolate);
        v8::Context::Scope context_scope(context);

        v8::Local<v8::Object> global = context->Global();
        JSRegister(global);


        Local<String> source = String::NewFromUtf8(isolate,"let myObject = new MyClass(); console.log(myObject.GetValue()); myObject.SetValue(24); myObject.log();").ToLocalChecked();
        Local<Script> script = Script::Compile(context,source).ToLocalChecked();
        Local<Value> result = script->Run(context).ToLocalChecked();

    }

    isolate->Dispose();
    v8::V8::Dispose();
    v8::V8::ShutdownPlatform();
    delete create_params.array_buffer_allocator;
}