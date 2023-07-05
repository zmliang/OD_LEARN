package com.lib.od.dynamic_program;

public class 爬楼梯 {
    public int  cliamStair(int n){
        if (n == 1){
            return 1;
        }
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i=3;i<n;i++){
            dp[i] =dp[i-1]+1+dp[i-2]+1;
        }
        return dp[n];
    }
}
