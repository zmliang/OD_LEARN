package com.lib.od;


/**
 * <p>小明每周上班都会拿到自己的工作清单，工作清单内包含 n 项工作，
 * 每项工作都有对应的耗时时间（单位 h）和报酬，工作的总报酬为所有已
 * 完成工作的报酬之和，那么请你帮小明安排一下工作，保证小明在指定的
 * 工作时间内工作收入最大化。</p>
 *
 * 输入描述
 * <p>输入的第一行为两个正整数 T，n。
 * <br> T 代表工作时长（单位 h， 0 < T < 1000000），
 * <br> n 代表工作数量（ 1 < n ≤ 3000）。
 * <br> 接下来是 n 行，每行包含两个整数 t，w。
 * <br> t 代表该工作消耗的时长（单位 h， t > 0），w 代表该项工作的报酬。</p>
 *
 *
 * 0-1背包问题
 * <p>工作时长T相当于背包承重</p>
 * <p>每一项工作相当于每件物品</p>
 * <p>工作消耗的时长相当于物品重量</p>
 * <p>工作的报酬相当于物品的价值</p>
 *
 */
public class 最大报酬 extends BaseTest{

    @Override
    protected void officialSolution() {
        int T = scanner.nextInt(); // 工作时长
        int n = scanner.nextInt(); // 工作数量
        int[][] works = new int[n][2]; // 工作清单，每个工作包含耗时和报酬
        for (int i = 0; i < n; i++) {
            works[i][0] = scanner.nextInt(); // 耗时
            works[i][1] = scanner.nextInt(); // 报酬
        }

        int minTime = Integer.MAX_VALUE; // 记录工作清单中最小的耗时
        for (int[] work : works) {
            minTime = Math.min(minTime, work[0]);
        }

        int[][] dp = new int[n + 1][T + 1]; // 动态规划数组
        for (int i = 1; i <= n; i++) {
            for (int j = minTime; j <= T; j++) {
                int last = dp[i - 1][j]; // 不选当前工作
                int current = works[i - 1][0] > j ? 0 : works[i - 1][1] + dp[i - 1][j - works[i - 1][0]]; // 选当前工作
                dp[i][j] = Math.max(last, current); // 取最大值
            }
        }
        System.out.print(dp[n][T]); // 输出最大报酬

    }

    @Override
    protected void mySolution() {

    }
}
