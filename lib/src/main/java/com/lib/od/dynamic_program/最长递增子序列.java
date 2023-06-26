package com.lib.od.dynamic_program;

import java.util.Arrays;

public class 最长递增子序列 {

    //leetcode 第300题
    public void func(int[] nums){
        int[] dp = new int[nums.length];//dp[i]表示i之前，包括i，以nums[i]结尾的最长递增子序列的长度
        Arrays.fill(dp,1);

        for (int i=0;i< nums.length;i++){//位置i的最长升序子序列等于j从0到i-1各个位置的最长升序子序列 + 1 的最大值。
            for (int j=0;j<i;j++){
                if (nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
        }

        int result = 0;
        for (int value:dp){
            result= Math.max(result,value);
        }

    }

}
