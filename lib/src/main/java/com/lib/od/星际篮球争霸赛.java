package com.lib.od;

import java.util.Arrays;

public class 星际篮球争霸赛 extends BaseTest{

    private int[] nums;
    private int[] cur;//元素为 当前每个子集的和
    private int s;

    public boolean canPartitionKSubsets(int[] nums, int k) {
        for (int v : nums) {
            s += v;
        }
        if (s % k != 0) {
            return false;
        }
        s /= k;
        cur = new int[k];
        Arrays.sort(nums);
        this.nums = nums;
        return dfs(nums.length - 1);
    }

    private boolean dfs(int i) {
        if (i < 0) {
            return true;
        }
        for (int j = 0; j < cur.length; ++j) {
            if (j > 0 && cur[j] == cur[j - 1]) {//我们在 cur[j - 1] 的时候已经完成了搜索
                continue;
            }
            cur[j] += nums[i];
            if (cur[j] <= s && dfs(i - 1)) {
                return true;
            }
            cur[j] -= nums[i];
        }
        return false;
    }



    public boolean canParSubset(int[] nums,int k,int total){
        if (total%k!=0){
            return false;
        }
        int n = nums.length;
        int perTotal = total/k;
        Arrays.sort(nums);
        if (nums[nums.length-1]>perTotal){
            return false;
        }
        boolean[] dp = new boolean[1<<n];//是否被遍历过
        int[] preSum = new int[1<<n];//当前状态下已经累加的和
        dp[0] = true;
        for (int i=0;i<1<<n;i++){//枚举所有状态
            if (!dp[i]){//如果没有被遍历过，跳过
                continue;
            }
            for (int j=0;j<n;j++){//枚举所有元素
                if (preSum[i]+ nums[i]>perTotal){
                    break;
                }
                if (((i>>j)&1) == 0){//元素j不在状态i中
                    int next = i|(1<<j);//将元素j加入到状态i中
                    if (!dp[next]){
                        preSum[next]=(preSum[i]+nums[j])%perTotal;
                        dp[next] = true;
                    }

                }
            }
        }

        return dp[1<<n-1];
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
