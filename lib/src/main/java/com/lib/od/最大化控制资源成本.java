package com.lib.od;
import java.util.Scanner;
import java.util.Arrays;
import java.util.PriorityQueue;
/**
 * 题目描述
 * 公司创新实验室正在研究如何最小化资源成本，最大化资源利用率，请你设计算法帮他们解决一个任务混部问题有
 * taskNum项任务，每个任务有开始时间 (startTime)，结束时间 (endTime) ，并行度 (parallelism)三个
 * 属性并行度是指这个任务运行时将会占用的服务器数量，一个服务器在每个时刻可以被任意任务使用但最多被一个
 * 任务占用，任务运行完成立即释放 (结束时刻不占用)。
 * 任务混部问题是指给定一批任务，让这批任务由同一批服务器承载运行.请你计算完成这批任务混部最少需要多少
 * 服务器，从而最大化控制资源成本
 * 输入描述
 * 第一行输入为taskNum，表示有taskNum项任务
 * 接下来taskNum行，每行三个整数，表示每个任务的
 * 开始时间 (startTime ) ，结束时间 (endTime ) ，并行度 (parallelism)
 * 输出描述
 * 一个整数，表示最少需要的服务器数量
 *
 * 题目解析
 * 本题其实是求解最大区间重叠个数的变种题。本题是要求解重叠区间的权重和。因此，我们需要定义一个
 * 变量来记录重叠区间的权重和当小顶堆弹出不重鲁区间时，变量需要减夫被弹出区间的权重，当我们向小
 * 顶堆只压入重鲁区间时，则变量需要加一被压入区间的权
 * 重
 * 参考:
 * 253.会议室 I1- 力扣 (LeetCode
 * 如果非会员可以查看下面的文章
 * [LeetCode - 253] 会议室 II 会议室2 leetcode 学哥斌的博客-CSDN博客
 * 新增:
 * 本题是一个经典的会议室安排问题，但是与一般的会议室安排问题不同的是，本题每个任务有一个并行度，
 * 即需要占用多少个服务器才能完成任务。因此，我们需要对会议室安排问题进行一定的变形。
 * 具体来说。我们可以将每个任务转化为一个区间，区间的开始时间为任务的开始时间，结束时间为任务的结束
 * 时间，区间的长度就是任务需要占用的服务器数量。然后，我们可以按照区间的开始时间从小到大排序，然后使用
 * 一个小根谁只来维护当前正在进行的任务，堆顶元素就是当前结束时间最早的任务。每当我们遇到一个新的任务时，
 * 我们将其加入堆中，并更新当前处理的结果。如果当前任务的开始时间比堆顶元素的结束时间还要晚，说明堆顶元素
 * 已经结束了，我们需要将其弹出，并从当前处理的结果中减去其占用的服务器数量。最终，我们可以得到最少需要的服务器数量。
 */
public class 最大化控制资源成本 extends BaseTest{

    public static int minMeetingRooms(int[][] ranges) {
        // 按照会议开始时间排序
        Arrays.sort(ranges, (a, b) -> a[0] - b[0]);

        // 用小根堆维护当前正在开的会议
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int maxAttendees = 0;
        int currentAttendees = 0;
        for (int i = 0; i < ranges.length; i++) {
            // 将已经结束的会议从小根堆中移除，并更新当前参加会议的人数
            while (!pq.isEmpty() && pq.peek()[0] < ranges[i][0]) {
                int[] top = pq.poll();
                currentAttendees -= top[1];
            }

            // 将当前会议加入小根堆，并更新当前参加会议的人数
            pq.offer(new int[]{ranges[i][1], ranges[i][2]});
            currentAttendees += ranges[i][2];

            // 更新最多参加会议的人数
            maxAttendees = Math.max(maxAttendees, currentAttendees);
        }

        return maxAttendees;
    }

    @Override
    protected void officialSolution() {

        int numOfRanges = scanner.nextInt();
        int[][] ranges = new int[numOfRanges][3];
        for (int i = 0; i < numOfRanges; i++) {
            ranges[i][0] = scanner.nextInt();
            ranges[i][1] = scanner.nextInt();
            ranges[i][2] = scanner.nextInt();
        }

        // 输出结果
        System.out.println(minMeetingRooms(ranges));
    }

    @Override
    protected void mySolution() {

    }
}
