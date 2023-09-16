package com.app.startup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

class XExecutor implements Executor {

    private final BlockingQueue<Runnable> blockingQueue;

    public XExecutor() {
        blockingQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public void execute(Runnable command) {
        blockingQueue.offer(command);
    }

    public Runnable take() throws InterruptedException {
        return blockingQueue.take();
    }

}
