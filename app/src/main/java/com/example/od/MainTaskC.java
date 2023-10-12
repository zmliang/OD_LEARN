package com.example.od;

import android.util.Log;

import com.app.startup.AbsTask;

import java.util.Arrays;
import java.util.List;

public class MainTaskC extends AbsTask {
    public static final String TASK_NAME = "MainTaskC";

    @Override
    public void run() {
        Log.d("zml", "run c");
    }

    @Override
    public List<String> dependencies() {
        return Arrays.asList(MainTaskA.TASK_NAME, MainTaskB.TASK_NAME);
    }

    @Override
    public String taskName() {
        return TASK_NAME;
    }
}
