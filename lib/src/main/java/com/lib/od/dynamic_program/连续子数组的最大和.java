package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

public class 连续子数组的最大和 extends BaseTest {

    public int dynamicProgram(int[] nums){
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i=1;i<nums.length;i++){
            dp[i] = Math.max(dp[i],dp[i-1]+dp[i]);
        }
        int maxResult = dp[0];
        for (int i=1;i<dp.length;i++){
            maxResult = Math.max(maxResult,dp[i]);
        }
        return maxResult;
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
