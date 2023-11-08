package com.pos.od;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//
//        new XAppStartUp.Builder()
//                .add(new MainTaskA())
//                .add(new MainTaskB())
//                .add(new MainTaskC())
//                .add(new TaskD())
//                .add(new TaskE())
//                .addTaskListener(new MonitorTaskListener("zml", true))
//                .setExecutorService(ThreadManager.getInstance().WORK_EXECUTOR)
//                .addOnProjectExecuteListener(new OnProjectListener() {
//                    @Override
//                    public void onProjectStart() {
//                        Log.i("zml","onProjectStart");
//                    }
//
//                    @Override
//                    public void onProjectFinish() {
//                        Log.i("zml","onProjectFinish");
//                    }
//
//                    @Override
//                    public void onStageFinish() {
//                        Log.i("zml","onStageFinish");
//                    }
//                })
//                .create()
//                .start();
    }
}
