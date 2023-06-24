package com.lib.od.dynamic_program;


import com.lib.od.BaseTest;

import java.util.Arrays;

/**
 * 0-1背包问题，动态规划
 */
public class 查找充电设备组合 extends BaseTest {

    public int getResult(int[] powers,int max){

        int min = Arrays.stream(powers).min().getAsInt();
        if (min>max){
            return 0;
        }
        //前i个充电设备中，总功率不超过j时的最大功率
        int[][] dp = new int[powers.length+1][max+1];
        int ret = 0;
        for (int i=1;i<= powers.length;i++){
            int power = powers[i-1];

            for (int j=1;j<=max;j++){//功率
                if (power>j){
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], power+dp[i-1][j-power]);
                }
            }

        }

        return dp[powers.length][max];
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
