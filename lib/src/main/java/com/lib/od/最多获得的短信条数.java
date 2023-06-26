package com.lib.od;

public class 最多获得的短信条数 extends BaseTest{
    @Override
    protected void officialSolution() {
        int budget = 0;//预算
        int[] prices = new int[]{};//price[i] 充值i+1元，获得的短信条数

        int[] dp = new int[budget+1];//充值i元所能得到的最多短信数
        for (int i=0;i<= prices.length;i++){//优惠券
            for (int j=0;j<=budget;j++){//尝试各种预算
                if (i!=0 && j!=0 && j>=i){
                    dp[j] = Math.max(dp[j],dp[j-i]+prices[i-1]);
                }
            }
        }
    }

    @Override
    protected void mySolution() {

    }
}
