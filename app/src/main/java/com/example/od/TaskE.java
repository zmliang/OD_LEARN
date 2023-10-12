package com.example.od;

import android.util.Log;

import com.app.startup.AbsTask;

import java.util.Arrays;
import java.util.List;

public class TaskE extends AbsTask {

    @Override
    public void run() {
        Log.d("zml", "run e");
    }

    @Override
    public List<String> dependencies() {
        return Arrays.asList(MainTaskB.TASK_NAME, TaskD.TASK_NAME);
    }

    @Override
    public String taskName() {
        return "TaskE";
    }

}
