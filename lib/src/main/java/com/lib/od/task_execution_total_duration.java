package com.lib.od;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * 任务总执行时长
 *
 * 题目描述
 * 任务编排服务负责对任务进行组合调度
 * 参与编排的任务有两种类型，其中一种执行时长为taskA，另一种执行时长为taskB.任务一旦开始执行不能被打断，
 * 且任务可连续执行。服务每次可以编排num个任务
 * 请编写一个方法，生成每次编排后的任务所有可能的总执行时长,
 * 输入描述
 * 第1行输入分别为第1种任务执行时长taskA.
 * 第2种任务执行时长taskB.
 * 这次要编排的任务个数num，以逗号分隔。
 * 注: 每种任务的数量都大于本次可以编排的任务数量
 * 。0 < taskA
 * 。0 < taskB
 * 0 <= num <= 100000
 * 输出描述
 * 数组形式返回所有总执行时时长，需要按从小到大排列。
 */
public class task_execution_total_duration extends BaseTest{

    @Override
    protected void officialSolution() {
        List<Integer> taskTimes = Arrays.stream(scanner.nextLine().split(",")) // 读取用户输入，将其转为数组
                .map(Integer::parseInt) // 将数组中的每个元素转为整数类型
                .collect(Collectors.toList()); // 将转换后的数组转为 List 集合

        int taskATime = taskTimes.get(0); // 获取任务 A 的处理时间
        int taskBTime = taskTimes.get(1); // 获取任务 B 的处理时间
        int num = taskTimes.get(2); // 获取总任务数

        // 用 TreeSet 存储结果，自动排序且去重
        Set<Integer> totalTimes = new TreeSet<>(); // 创建 TreeSet 对象，用于存储结果
        for (int i = 0; i <= num; i++) { // 遍历所有可能的情况
            int res = taskATime * (num - i) + i * taskBTime; // 计算总耗时
            totalTimes.add(res); // 将计算结果添加到 TreeSet 中
        }

        // 输出结果
        System.out.println(totalTimes); // 将 TreeSet 中的结果输出到控制台
    }

    @Override
    protected void mySolution() {

    }
}
