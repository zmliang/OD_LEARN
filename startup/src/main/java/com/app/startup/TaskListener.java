package com.app.startup;

public interface TaskListener {

    void onWaitRunning(AbsTask task);
    void onStart(AbsTask task);
    void onFinished(AbsTask task,long waitTime,long consueTime);

}
