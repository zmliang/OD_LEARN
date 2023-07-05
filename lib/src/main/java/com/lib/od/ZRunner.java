package com.lib.od;

import com.lib.od.binary_tree.BinaryTree;
import com.lib.od.dynamic_program.单词拆分;
import com.lib.od.leetcode.LeetCodeTest;
import com.lib.od.回溯.全排列;
import com.lib.od.回溯.单词搜索2;
import com.lib.od.回溯.组合;
import com.lib.od.字符串.N字型变换;
import com.lib.od.栈.移掉k位数字;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZRunner {


    public static void main(String[] args) {
      //new 最多几个直角三角形().officialSolution();

     // new LeetCodeTest().merge(new int[][]{{1,3},{5,6}});

      int n = 5;
      List<String> operations = new ArrayList<>();
      int count = 0;
      int size = 0;
      boolean inorder = true;
      for (String operation:operations){
          if (operation.equals("head")){
              if (size != 0 && inorder){
                  inorder = false;
              }
              size++;
          }else if (operation.equals("tail")){
              size++;
          }else {
              if (size == 0){
                  continue;
              }
              if (!inorder){
                  count++;
                  inorder =true;
              }
              size--;
          }
      }

    }


    public void sort(int[] nums,int start,int end,int[] tmp){
        if (start >= end){
            return;
        }
        int mid = (end-start)/2;
        sort(nums,start,mid,tmp);
        sort(nums,mid+1,end,tmp);

        int start1 = start;
        int end1 = mid;
        int start2 = mid+1;
        int end2 = end;
        int index = start;
        while (start1<=end1 && start2<=end2){
            if (nums[start1]<nums[start2]){
                tmp[index] = nums[start1];
                start1++;
            }else {
                tmp[index] = nums[start2];
                start2++;
            }
            index++;
        }
        while (start1<=end1){
            tmp[index++] = nums[start1++];
        }
        while (start2<=end2){
            tmp[index++] = nums[start2++];
        }

    }


    /**
     * 动态规划，312戳气球
     * <p>
     * 动态规划常常适用于有重叠子问题和最优子结构性质的问题，
     * 并且记录所有子问题的结果，因此动态规划方法所耗时间往往远少于朴素解法。
     * <p>
     * 动态规划有自底向上和自顶向下两种解决问题的方式。自顶向下即记忆化递归，自底向上就是递推。
     * <p>
     * 使用动态规划解决的问题有个明显的特点，一旦一个子问题的求解得到结果，以后的计算过程就不会修改它，
     * 这样的特点叫做无后效性，求解问题的过程形成了一张有向无环图。动态规划只解决每个子问题一次，
     * 具有天然剪枝的功能，从而减少计算量。
     */
    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 2][n + 2];
        int[] val = new int[n + 2];
        val[0] = val[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            val[i] = nums[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 2; j <= n + 1; j++) {
                for (int k = i + 1; k < j; j++) {
                    int sum = val[i] * val[k] * val[j];
                    sum += dp[i][k] + dp[k][j];
                    dp[i][j] = Math.max(dp[i][j], sum);
                }
            }
        }
        return dp[0][n + 1];
    }

    /**
     * 回溯算法是纯暴力穷举
     * 回溯算法的核心思路：
     * 1：路径，也就是已经做出的选择
     * 2：选择条件
     * 3：结束条件
     */
    /**
     * 全排列
     */
    static List<List<Integer>> result = new ArrayList<>();//回溯结果

    private static void permute(int[] nums) {
        LinkedList<Integer> result = new LinkedList<>();
        backtrace(result, nums);
        System.out.println(result);
    }

    private static void backtrace(LinkedList<Integer> path, int[] nums) {
        if (path.size() == nums.length) {
            result.add(new LinkedList<>(path));
            return;
        }
        for (int i=0;i<nums.length;i++){
            if (path.contains(nums[i])){
                continue;
            }
            path.add(nums[i]);//做选择
            backtrace(path,nums);
            path.removeLast();//取消选择
        }
    }


}




















