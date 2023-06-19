package com.lib.od;

import java.util.ArrayList;
import java.util.HashMap;

public class 最多等和不相交连续子序列 extends BaseTest{

    public static int getMaxSubsequenceCount(int[] nums, int n) {
        // 记录相同和连续子序列的区间
        HashMap<Integer, ArrayList<Integer[]>> sumRanges = new HashMap<>();

        // 求解nums数组的前缀和数组prefixSum
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i]; // 计算前缀和
        }

        // 利用前缀和求差，求出所有连续子序列的和
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == 0) {
                    int sum = prefixSum[j];
                    sumRanges.putIfAbsent(sum, new ArrayList<>());
                    sumRanges.get(sum).add(new Integer[] {0, j}); // 添加区间
                } else {
                    int sum = prefixSum[j] - prefixSum[i - 1];
                    sumRanges.putIfAbsent(sum, new ArrayList<>());
                    sumRanges.get(sum).add(new Integer[] {i, j}); // 添加区间
                }
            }
        }

        // 保存相同和不相交连续子序列的最大个数
        int maxCount = 0;
        for (Integer sum : sumRanges.keySet()) {
            ArrayList<Integer[]> range = sumRanges.get(sum);
            maxCount = Math.max(maxCount, getMaxDisjointSubsequenceCount(range)); // 求出最大不相交连续子序列的个数
        }

        return maxCount;
    }

    //  求不相交区间的最大个数
    public static int getMaxDisjointSubsequenceCount(ArrayList<Integer[]> ranges) {
        int count = 1; // 至少一个
        ranges.sort((a, b) -> a[1] - b[1]); // 按右端点排序

        Integer rightEnd = ranges.get(0)[1];
        for (int i = 1; i < ranges.size(); i++) {
            Integer[] range = ranges.get(i);
            Integer leftEnd = range[0];
            Integer rightEndOfRange = range[1];

            if (rightEnd < leftEnd) { // 如果当前区间与前面的区间不相交
                count++;
                rightEnd = rightEndOfRange;
            }
        }
        return count;
    }




    @Override
    void officialSolution() {
        int n = scanner.nextInt(); // 输入序列的长度

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt(); // 输入序列
        }

        System.out.println(getMaxSubsequenceCount(nums, n)); // 输出最大不相交连续子序列的个数

    }

    @Override
    protected void mySolution() {

    }
}
