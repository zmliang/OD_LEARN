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

static void _log(const v8::FunctionCallbackInfo<v8::Value>& args) {
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
    function_t->PrototypeTemplate()->Set(isolate,"log",FunctionTemplate::New(isolate,_log));

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



extern "C"
JNIEXPORT void JNICALL
Java_com_zml_v8_v8native_register(JNIEnv *env, jobject thiz) {
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
