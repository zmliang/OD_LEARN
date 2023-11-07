package com.pos.od;

import android.util.Log;

import com.app.startup.AbsTask;

import java.util.Arrays;
import java.util.List;

public class TaskD extends AbsTask {
    public static final String TASK_NAME = "TaskD";

    @Override
    public void run() {
        Log.d("zml", "run d");
    }

    @Override
    public List<String> dependencies() {
        return Arrays.asList(MainTaskC.TASK_NAME);
    }

    @Override
    public String taskName() {
        return TASK_NAME;
    }
}
