package com.example.od;

import android.util.Log;

import com.app.startup.AbsTask;

import java.util.List;

public class MainTaskB extends AbsTask {
    public static final String TASK_NAME = "MainTaskB";

    @Override
    public void run() {
        Log.d("zml", "run b");
    }

    @Override
    public List<String> dependencies() {
        return null;
    }

    @Override
    public String taskName() {
        return TASK_NAME;
    }
}
