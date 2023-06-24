package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

public class 背包问题 extends BaseTest {

    public static int knapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            int value = values[i - 1];
            int weight = weights[i - 1];

            for (int j = 1; j <= capacity; j++) {
                if (weight <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j],
                            dp[i - 1][j - weight] + value);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][capacity];
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
