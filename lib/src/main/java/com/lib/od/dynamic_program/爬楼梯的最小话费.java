package com.lib.od.dynamic_program;

public class 爬楼梯的最小话费 {
    public int minCost(int[] costs){
        int[][] dp = new int[costs.length][2];
        for (int i=0;i<costs.length;i++){
            for (int j=0;j<2;j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i=0;i<costs.length;i++){
            dp[i][0] = Math.min(dp[i-1][0],dp[i-1][1]);
            dp[i][1] = dp[i][0]+costs[i];
        }

        return Math.min(dp[costs.length-1][0],dp[costs.length-1][1]);

    }
}
