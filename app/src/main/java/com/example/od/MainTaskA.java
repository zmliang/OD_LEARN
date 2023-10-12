package com.example.od;

import android.util.Log;

import com.app.startup.AbsTask;

import java.util.List;

public class MainTaskA extends AbsTask {
    public static final String TASK_NAME = "MainTaskA";

    @Override
    public void run() {
        Log.d("zml", "run a");
    }

    @Override
    public List<String> dependencies() {
        return null;
    }

    @Override
    public String taskName() {
        return TASK_NAME;
    }

    @Override
    public boolean isWaitOnMainThread() {
        return true;
    }
}
