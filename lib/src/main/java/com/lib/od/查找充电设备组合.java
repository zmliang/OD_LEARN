package com.lib.od;

public class 查找充电设备组合 extends BaseTest{
    @Override
    protected void officialSolution() {
        int[] array = new int[]{50,20,20,60};
        int n = array.length;
        int max = 90;

        int[][] dp = new int[n+1][max+1];

        for (int i=1;i<=n;i++){//物品个数
            for (int j=1;j<=max;j++){//容量
                if (array[i-1]>j){//比当前总功率大，不选
                    dp[i][j] =dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-array[i-1]]+array[i-1]);
                }
            }
        }


    }

    @Override
    protected void mySolution() {

    }
}
