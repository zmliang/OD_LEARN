package com.example.od;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zml.opengl.NativeLib;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ZML","StringFromJNI="+new NativeLib().stringFromJNI());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
