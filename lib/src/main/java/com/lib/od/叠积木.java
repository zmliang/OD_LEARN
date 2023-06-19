package com.lib.od;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class 叠积木 extends BaseTest{

    public static boolean dfs(List<Integer> nums, int cur, int used, List<Integer> bucket, int k, int score) {
        // 所有积木都已经使用完，返回true
        if (cur < 0) {
            return true;
        }
        // 当前层还未堆满，继续往上堆
        if (used < k) {
            bucket.set(used, nums.get(cur));
            if (dfs(nums, cur - 1, used + 1, bucket, k, score)) {
                return true;
            }
            bucket.set(used, 0);
        }
        // 把当前积木加入已有的每一层中，看是否能够满足条件
        for (int i = 0; i < used; i++) {
            // 如果当前层和上一层积木长度相同，则不需要重复计算
            if (i > 0 && bucket.get(i).equals(bucket.get(i - 1))) {
                continue;
            }
            // 如果当前积木可以放入当前层，则把当前积木放入当前层
            if (bucket.get(i) + nums.get(cur) <= score) {
                bucket.set(i, bucket.get(i) + nums.get(cur));
                if (dfs(nums, cur - 1, used, bucket, k, score)) {
                    return true;
                }
                bucket.set(i, bucket.get(i) - nums.get(cur));
            }
        }
        return false;
    }



    @Override
    void officialSolution() {
        String inputStr = scanner.nextLine();
        List<Integer> nums = new ArrayList<>();
        for (String str : inputStr.split(" ")) {
            nums.add(Integer.parseInt(str));
        }
        // 求和并排序
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        Collections.sort(nums);

        int res = -1;
        // i表示可以堆的层数
        for (int i = 2; i <= sum / 2; i++) {
            // 如果所有数字的和除不尽层数，自然肯定不满足条件
            if (sum % i != 0) {
                continue;
            }
            // 每一层的长度为score
            int score = sum / i;
            // 如果最大的积木长度大于当前层的长度，则无法满足条件
            if (nums.get(nums.size() - 1) > score) {
                continue;
            }
            // 建立一个长度为k的桶
            List<Integer> bucket = new ArrayList<>(Collections.nCopies(i, score));
            if (dfs(nums, nums.size() - 1, 0, bucket, i, score)) {
                res = Math.max(res, i);
            }
        }
        System.out.println(res);
    }

    @Override
    protected void mySolution() {

    }
}
