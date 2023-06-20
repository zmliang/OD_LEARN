package com.lib.od;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 通信误码
 *
 * 题目描述
 * 信号传播过程中会出现一些误码，不同的数字表示不同的误码ID，取值范围为1~65535，用一个数组记录误码出现的情况
 * 每个误码出现的次数代表误码频度，请找出记录中包含频度最高误码的最小子数组长度
 * 输入描述
 * 误码总数目:取值范围为0~255，取值为0表示没有误码的情况。误码出现频率数组:误码ID范围为165535，数组长度为11000
 * 输出描述
 * 包含频率最高的误码最小子数组长度
 */
public class communication_error_code extends BaseTest{



    @Override
    protected void officialSolution() {
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (count.containsKey(nums[i])) {
                count.put(nums[i], count.get(nums[i]) + 1);
            } else {
                count.put(nums[i], 1);
            }
        }
        int maxCount = 0;
        for (int key : count.keySet()) {
            maxCount = Math.max(maxCount, count.get(key));
        }
        int result = n;
        for (int key : count.keySet()) {
            if (count.get(key) == maxCount) {
                int first = -1;
                int last = -1;
                for (int i = 0; i < n; i++) {
                    if (nums[i] == key) {
                        if (first == -1) {
                            first = i;
                        }
                        last = i;
                    }
                }
                result = Math.min(result, last - first + 1);
            }
        }
        System.out.println(result);
    }

    @Override
    protected void mySolution() {

    }
}
