package com.app.startup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public abstract class AbsTask {
    public static final int STATE_IDLE = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_WAIT = 3;

    private Executor executor;

    private int waitCount = 0;
    private volatile int currentState = STATE_IDLE;

    private List<AbsTask> taskList;//后继节点
    private TaskListener taskListener;


    void start(){
        if (currentState!=STATE_IDLE){
            throw new RuntimeException("the task "+taskName()+" already running");
        }
        final long startTime = System.currentTimeMillis();
        this.setCurrentState(STATE_WAIT);
        if (taskListener!=null){
            taskListener.onWaitRunning(this);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setCurrentState(STATE_RUNNING);
                long dw = System.currentTimeMillis()-startTime;
                if (taskListener!=null){
                    taskListener.onStart(AbsTask.this);
                }
                try {
                    AbsTask.this.run();
                }catch (Exception e){
                    throw e;
                }
                setCurrentState(STATE_FINISHED);
                long df = System.currentTimeMillis()-startTime;
                if (taskListener!=null){
                    taskListener.onFinished(AbsTask.this,dw,df);
                }
                notifyFinished();
            }
        };
        executor.execute(runnable);
    }


    private void notifyFinished(){
        if (taskList!=null && !taskList.isEmpty()){
            SortUtil.sort(taskList);
            for (AbsTask task : taskList){
                task.onDepTaskFinished();
            }
        }
    }

    /**
     * 后继节点将会检查依赖任务是否全部完成，如果全部完成就执行当前任务。
     */
    private void onDepTaskFinished(){
        int size;
        synchronized (this){
            waitCount--;
            size = waitCount;
        }
        if (size == 0){
            start();
        }
    }

    void addDependencies(AbsTask task){
        if (currentState!=STATE_IDLE){
            return;
        }
        waitCount++;
        task.addTaskNode(this);
    }

    private void addTaskNode(AbsTask task){
        if (task == this){
            throw new RuntimeException("could not same task");
        }
        if (taskList == null){
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }

    void setExecutor(Executor executor){
        this.executor = executor;
    }

    void setTaskListener(TaskListener listener){
        this.taskListener = listener;
    }


    public int priority(){
        return 0;
    }

    public abstract void run();
    public abstract List<String> dependencies();
    public abstract String taskName();


    public boolean isWaitOnMainThread(){
        return false;
    }

    public boolean isRunOnMainThread(){
        return false;
    }

    public boolean isFinished(){
        return currentState == STATE_FINISHED;
    }

    public boolean isInStage(){
        return true;
    }

    private void setCurrentState(int currentState) {
        this.currentState = currentState;
    }



    @Override
    public boolean equals(Object obj) {
        return obj!=null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
