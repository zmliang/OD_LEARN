package com.app.startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public final class XAppStartUp {
    private CountDownLatch waitCountDownLatch;
    private AtomicInteger atomicMainTaskCount;
    private XExecutor mainExecutor;
    private final List<TaskListener> taskListeners;
    private final List<OnProjectListener> projectListeners;
    private final AtomicInteger remainingStageTaskCount;
    private final AtomicInteger remainingTaskCount;
    private final List<AbsTask> startTaskNodes;
    private final Map<String, AbsTask> taskMap;

    private Executor getMainExecutor() {
        if (mainExecutor == null) {
            mainExecutor = new XExecutor();
        }
        return mainExecutor;
    }

    private XAppStartUp(Builder builder){
        this.taskListeners = builder.taskListeners;
        this.projectListeners = builder.projectListeners;

        ThreadPoolExecutor threadPoolExecutor = builder.threadPoolExecutor;

        TaskListener defaultTaskListener = new TaskStateListener();
        int inStageSize = 0;
        int waitCount = 0;

        taskMap = builder.taskMap;

        startTaskNodes = new ArrayList<>();
        int mainTaskCount = 0;
        for (AbsTask task: builder.tasks){
            if (task.isRunOnMainThread()){
                task.setExecutor(getMainExecutor());
                mainTaskCount++;
            }else {
                task.setExecutor(threadPoolExecutor);
            }
            if (task.isInStage()){
                inStageSize++;
            }
            if (task.isWaitOnMainThread()){
                waitCount++;
            }

            task.setTaskListener(defaultTaskListener);
            List<String> dependencies = task.dependencies();
            if (dependencies!=null && !dependencies.isEmpty()){
                for (String depTaskName:dependencies){
                    AbsTask task1 = taskMap.get(depTaskName);
                    if (task!=null){
                        task.addDependencies(task1);
                    }
                }
            }else {
                startTaskNodes.add(task);
            }
        }
        SortUtil.sort(startTaskNodes);
        this.remainingTaskCount = new AtomicInteger(taskMap.size());
        this.remainingStageTaskCount = new AtomicInteger(inStageSize);
        if (mainTaskCount>0){
            atomicMainTaskCount = new AtomicInteger(mainTaskCount);
        }
        if (waitCount>0){
            waitCountDownLatch = new CountDownLatch(waitCount);
        }

    }

    public void start(){
        if (startTaskNodes.isEmpty()){
            return;
        }
        onProjectStart();
        for (AbsTask task:startTaskNodes){
            task.start();
        }
        while (atomicMainTaskCount!=null && atomicMainTaskCount.get()>0){
            try {
                Runnable runnable = mainExecutor.take();
                if (runnable!=null){
                    runnable.run();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (waitCountDownLatch != null &&  waitCountDownLatch.getCount() > 0) {
            try {
                waitCountDownLatch.await();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }

    private void onProjectFinish(){
        if (projectListeners != null){
            for (OnProjectListener listener:projectListeners){
                listener.onProjectFinish();
            }
        }
        for (AbsTask task:taskMap.values()){
            if (!task.isFinished()){
                //todo
            }
        }

    }

    private void onProjectStart(){
        if (projectListeners == null){
            return;
        }
        for (OnProjectListener listener:projectListeners){
            listener.onProjectStart();
        }
    }

    private void notifyStageFinish(){
        for (OnProjectListener listener:projectListeners){
            listener.onStageFinish();
        }
    }

    public void addProjectExecuteListener(OnProjectListener listener){
        projectListeners.add(listener);
    }

    public static class Builder {

        private final List<OnProjectListener> projectListeners = new ArrayList<>();
        private ThreadPoolExecutor threadPoolExecutor;
        private final List<TaskListener> taskListeners = new ArrayList<>();
        private final Map<String, AbsTask> taskMap = new HashMap<>();
        //private Config config;
        private List<AbsTask> tasks;
        //private Logger logger;

        public XAppStartUp create() {
            return new XAppStartUp(this);
        }

        public Builder addOnProjectExecuteListener(OnProjectListener listener) {
            projectListeners.add(listener);
            return Builder.this;
        }

        public Builder addTaskListener(TaskListener listener) {
            taskListeners.add(listener);
            return Builder.this;
        }


        public Builder add(AbsTask task) {
            AbsTask addedTask = taskMap.get(task.taskName());
            if (addedTask != null) {
                String ep = task.getClass().getSimpleName() + " " + task.taskName()
                        + addedTask.getClass().getSimpleName() + " " + addedTask.taskName();
                throw new RuntimeException(ep + " mu task");
            }
            if (task.taskName() == null) {
                throw new IllegalStateException("task name null");
            }
            if (tasks == null) {
                tasks = new ArrayList<>();
            }
            tasks.add(task);
            taskMap.put(task.taskName(), task);
            return Builder.this;
        }

        public Builder setExecutorService(ThreadPoolExecutor threadPoolExecutor) {
            this.threadPoolExecutor = threadPoolExecutor;
            return Builder.this;
        }
    }

    private class TaskStateListener implements TaskListener {

        @Override
        public void onWaitRunning(AbsTask task) {

        }

        @Override
        public void onStart(AbsTask task) {
            for (TaskListener taskListener : taskListeners) {
                taskListener.onStart(task);
            }
        }

        @Override
        public void onFinished(AbsTask task, long dw, long df) {
            for (TaskListener taskListener : taskListeners) {
                taskListener.onFinished(task, dw, df);
            }

            if (task.isWaitOnMainThread()) {
                waitCountDownLatch.countDown();
            } else if (task.isRunOnMainThread()) {
                atomicMainTaskCount.decrementAndGet();
            }

            if (task.isInStage()) {
                int size = remainingStageTaskCount.decrementAndGet();
                if (size == 0) {
                    notifyStageFinish();
                }
            }

            int size = remainingTaskCount.decrementAndGet();
            if (size == 0) {
                onProjectFinish();
            }
        }

    }

}
