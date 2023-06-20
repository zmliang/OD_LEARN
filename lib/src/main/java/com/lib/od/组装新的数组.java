package com.lib.od;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目描述
 * 给你一个整数M和数组N，N中的元素为连续整数，要求根据N中的元素组装成新的数组R，组装规则:
 * 1.R中元素总和加起来等于M
 * 2.R中的元素可以从N中重复选取
 * 3.R中的元素最多只能有1个不在N中，且比N中的数字都要小 (不能为负数)
 *
 * 总共有多少种组装方式
 */
public class 组装新的数组 extends BaseTest{

    // 递归函数，arr为原数组，index为当前处理元素的下标，sum为当前组合的元素总和，min为当前组合中最小的元素，m为目标总和，count为符合要求的组合数量
    public static int dfs(List<Integer> arr, int index, int sum, int min_num, int m, int count) {
        if (sum > m) { // 元素总和超过目标总和，返回符合要求的组合数量
            return count;
        }
        if (sum == m || (m - sum < min_num && m - sum > 0)) { // 元素总和等于目标总和或者目标总和减去元素总和小于当前组合中最小的元素且大于0，返回符合要求的组合数量加1
            return count + 1;
        }
        for (int i = index; i < arr.size(); i++) { // 从当前元素开始，遍历原数组中的元素
            count = dfs(arr, i, sum + arr.get(i), min_num, m, count); // 递归调用dfs，更新符合要求的组合数量
        }
        return count;
    }


    @Override
    protected void officialSolution() {
        String line = scanner.nextLine();
        List<Integer> arr = new ArrayList<>();
        String[] nums = line.split(" ");
        for (String num : nums) { // 将输入的字符串转换为数组
            arr.add(Integer.parseInt(num));
        }
        int m = scanner.nextInt();
        List<Integer> new_arr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) { // 将小于等于目标总和的元素添加到新数组中
            if (arr.get(i) <= m) {
                new_arr.add(arr.get(i));
            }
        }
        int min_num = new_arr.get(0); // 记录新数组中最小的元素
        System.out.println(dfs(new_arr, 0, 0, min_num, m, 0)); // 输出符合要求的组合数量
    }

    @Override
    protected void mySolution() {

    }
}
