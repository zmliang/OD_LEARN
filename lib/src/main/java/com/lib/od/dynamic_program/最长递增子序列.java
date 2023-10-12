package com.lib.od.dynamic_program;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

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

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];//dp[i] 以下表i结尾的子数组里最长递增序列数
        dp[0] = 0;
        int max = 1;
        for (int end = 1;end< nums.length;end++){//
            for (int left = 0;left<end;left++){//循环遍历从left---end这个区间
                if (nums[end]>nums[left]){//比end下标里的数小的，才尝试更新，
                    dp[end] = Math.max(dp[end],dp[left]+1);
                }
            }
            max = Math.max(max,dp[end]);
        }
        return max;
    }

}
