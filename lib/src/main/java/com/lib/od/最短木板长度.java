package com.lib.od;


import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * <p>小明有 n 块木板，第 i ( 1 ≤ i ≤ n ) 块木板长度为 ai。
 * <br> 小明买了一块长度为 m 的木料，这块木料可以切割成任意块，拼接到已有的木板上，用来加长木板。
 * <br> 小明想让最短的模板尽量长。请问小明加长木板后，最短木板的长度可以为多少？</p>
 *
 * 输入描述：
 * <p>输入的第一行包含两个正整数， n ( 1 ≤ n ≤ 10^3 ), m ( 1 ≤ m ≤ 10^6 )，
 * n 表示木板数， m 表示木板长度。<br>
 * 输入的第二行包含 n 个正整数， a1, a2,…an ( 1 ≤ ai ≤ 10^6 )。</p>
 *
 * 输出描述：
 * <p>输出的唯一一行包含一个正整数，表示加长木板后，最短木板的长度最大可以为多少？</p>
 *
 * 思路：
 * 贪心算法
 * <p>循环遍历木料的长度，每次都给最短的木板补一米的长度。补完之后重新排序，重复补一米的操作。知道木料用完。</p>
 */
public class 最短木板长度 extends BaseTest{

    @Override
    void officialSolution() {
        int n = scanner.nextInt(); // 木板数量
        int m = scanner.nextInt(); // 切割次数
        scanner.nextLine();
        int[] woodLengths = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray(); // 木板长度数组

        // 统计每种长度的木板数量
        HashMap<Integer, Integer> woodCountMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            woodCountMap.put(woodLengths[i], woodCountMap.getOrDefault(woodLengths[i], 0) + 1);
        }

        // 将每种长度的木板按长度升序排序
        PriorityQueue<int[]> woodPriorityQueue = new PriorityQueue<>((wood1, wood2) -> wood1[0] - wood2[0]);
        for (int length : woodCountMap.keySet()) {
            woodPriorityQueue.offer(new int[]{length, woodCountMap.get(length)});
        }

        while (m > 0) {
            // 特殊情况，只有一种长度
            if (woodPriorityQueue.size() == 1) {
                int[] woodKind = woodPriorityQueue.poll();
                System.out.println(woodKind[0] + m / woodKind[1]); // 输出最终的木板长度
                return;
            } else {
                int[] woodKind1 = woodPriorityQueue.poll(); // 取出长度最小的一种木板
                int[] woodKind2 = woodPriorityQueue.peek(); // 取出长度次小的一种木板

                int diff = woodKind2[0] - woodKind1[0]; // 两种木板长度的差值
                int total = diff * woodKind1[1]; // 可以切割的木板数量

                if (total > m) { // 如果可以切割的木板数量大于剩余切割次数
                    System.out.println(woodKind1[0] + m / woodKind1[1]); // 输出最终的木板长度
                    return;
                } else if (total == m) { // 如果可以切割的木板数量等于剩余切割次数
                    System.out.println(woodKind2[0]); // 输出最终的木板长度
                    return;
                } else {
                    m -= total; // 减去已经切割的木板数量
                    woodKind2[1] += woodKind1[1]; // 将两种木板合并
                }
            }
        }

        System.out.println(woodPriorityQueue.peek()[0]); // 输出最终的木板长度

    }

    @Override
    protected void mySolution() {

    }
}
