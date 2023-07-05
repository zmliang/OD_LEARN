package com.lib.od.dynamic_program;

public class leetCode硬币交换 {

    /**
     * 凑出amount面值需要多少枚硬币
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins,int amount){
        int len = coins.length;
        int[] dp = new int[amount+1];
        dp[0] = 0;

        for (int i=1;i<amount+1;i++){
            dp[i] = Integer.MAX_VALUE;
            for(int j=0;j<len;j++){
                if (i>=coins[j]){
                    dp[i] = Math.min(dp[i],dp[i-coins[j]]+1);
                }
            }
        }
        return dp[amount];
    }

    /**
     * 机器人有多少种方法，从左上角走到右下角
     * @param m
     * @param n
     * @return
     */
    public int paths(int m,int n){
        int[][] dp = new int[m][n];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if (i == 0 || j == 0){
                    dp[i][j] = 1;
                }else {
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }


}
