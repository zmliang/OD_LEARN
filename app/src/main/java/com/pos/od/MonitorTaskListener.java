package com.pos.od;

import android.util.Log;

import androidx.core.os.TraceCompat;

import com.app.startup.AbsTask;
import com.app.startup.TaskListener;

public class MonitorTaskListener implements TaskListener {

    private String tag;
    private boolean isLog;

    public MonitorTaskListener(String tag, boolean isLog) {
        this.tag = tag;
        this.isLog = isLog;
    }

    @Override
    public void onWaitRunning(AbsTask task) {
    }

    @Override
    public void onStart(AbsTask task) {
        if (isLog) {
            Log.d(tag + "-START", "task start :" + task.taskName());
        }
        TraceCompat.beginSection(task.taskName());
        if (task.isWaitOnMainThread()) {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        }
    }


    @Override
    public void onFinished(AbsTask task, long dw, long df) {
        if (task.isWaitOnMainThread()) {
            android.os.Process.setThreadPriority(ThreadManager.DEFAULT_PRIORITY);
        }
        TraceCompat.endSection();
        if (isLog) {
            Log.d(tag + "-END", "task end :" + task.taskName() + " wait " + dw + " cost " + df);
        }
    }

}
