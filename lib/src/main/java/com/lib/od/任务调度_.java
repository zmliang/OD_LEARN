package com.lib.od;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class 任务调度_ extends BaseTest{



    /**
     * 对任务列表进行调度
     * @param taskList 任务列表
     */
    public static void getResult(List<Task> taskList) {
        // 创建优先队列，按照任务优先级和到达时间排序
        PriorityQueue<Task> priorityQueue =
                new PriorityQueue<>(
                        (a, b) -> a.priority != b.priority ? b.priority - a.priority : a.arrivedTime - b.arrivedTime);

        // 将第一个任务加入队列
        priorityQueue.offer(taskList.remove(0));
        int currentTime = priorityQueue.peek().arrivedTime; // 记录当前时间

        // 进行任务调度
        while (!taskList.isEmpty()) {
            Task currentTask = priorityQueue.peek(); // 获取当前任务
            Task nextTask = taskList.remove(0); // 获取下一个任务
            int currentTaskEndTime = currentTime + currentTask.executeTime; // 计算当前任务结束时间

            // 如果下一个任务到达时间在当前任务执行结束之前，则需要将当前任务执行时间缩短
            if (currentTaskEndTime > nextTask.arrivedTime) {
                currentTask.executeTime -= nextTask.arrivedTime - currentTime;
                currentTime = nextTask.arrivedTime;
            } else { // 否则，执行当前任务并将下一个任务加入队列
                priorityQueue.poll();
                System.out.println(currentTask.taskId + " " + currentTaskEndTime);
                currentTime = currentTaskEndTime;

                // 如果下一个任务到达时间在当前任务执行结束之后，则需要将队列中的空闲任务执行完毕
                if (nextTask.arrivedTime > currentTaskEndTime) {
                    while (!priorityQueue.isEmpty()) {
                        Task idleTask = priorityQueue.peek();
                        int idleTaskEndTime = currentTime + idleTask.executeTime;

                        // 如果空闲任务执行结束时间在下一个任务到达时间之前，则执行空闲任务
                        if (idleTaskEndTime > nextTask.arrivedTime) {
                            idleTask.executeTime -= nextTask.arrivedTime - currentTime;
                            break;
                        } else { // 否则，执行空闲任务并更新当前时间
                            priorityQueue.poll();
                            System.out.println(idleTask.taskId + " " + idleTaskEndTime);
                            currentTime = idleTaskEndTime;
                        }
                    }
                    currentTime = nextTask.arrivedTime;
                }
            }
            priorityQueue.offer(nextTask);
        }

        // 执行队列中剩余的任务
        while (!priorityQueue.isEmpty()) {
            Task pollTask = priorityQueue.poll();
            int pollTaskEndTime = currentTime + pollTask.executeTime;
            System.out.println(pollTask.taskId + " " + pollTaskEndTime);
            currentTime = pollTaskEndTime;
        }
    }

    class Task {
        int taskId; // 任务编号
        int priority; // 任务优先级
        int executeTime; // 任务执行时间
        int arrivedTime; // 任务到达时间

        public Task(int taskId, int priority, int executeTime, int arrivedTime) {
            this.taskId = taskId;
            this.priority = priority;
            this.executeTime = executeTime;
            this.arrivedTime = arrivedTime;
        }
    }



    @Override
    protected void officialSolution() {
        List<Task> taskList = new ArrayList<>();

        // 读取输入并创建任务列表
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            String[] inputs = input.split(" ");
            int taskId = Integer.parseInt(inputs[0]);
            int priority = Integer.parseInt(inputs[1]);
            int executeTime = Integer.parseInt(inputs[2]);
            int arrivedTime = Integer.parseInt(inputs[3]);
            Task task = new Task(taskId, priority, executeTime, arrivedTime);
            taskList.add(task);
        }

        // 调用 getResult 方法进行任务调度
        getResult(taskList);
    }

    @Override
    protected void mySolution() {

    }
}
