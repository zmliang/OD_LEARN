package com.lib.od;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * K优雅子数组
 *
 */
public class k_grace_array extends BaseTest{

    @Override
    protected void officialSolution() {
        // 处理输入
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int result = 0;

        int left = 0;
        int right = 0;
        // 统计每个数字出现的次数
        HashMap<Integer, Integer> numCount = new HashMap<>();

        while (left < n && right < n) {
            // 右指针向右移动
            Integer num = nums[right];
            numCount.put(num, numCount.getOrDefault(num, 0) + 1);
            // 如果当前数字出现次数大于等于k，则以右指针为结尾的子数组都是优雅子数组
            if (numCount.get(num) >= k) {
                result += n - right;
                // 左指针向右移动，缩小子数组范围
                numCount.put(nums[left], numCount.get(nums[left]) - 1);
                left++;
                // 右指针向左移动，减少当前数字出现次数
                numCount.put(num, numCount.get(num) - 1);
                right--;
            }
            right++;
        }
        System.out.println(result);
    }

    @Override
    protected void mySolution() {
        int arrayLen = scanner.nextInt();
        int k = scanner.nextInt();
        int array[] = new int[arrayLen];
        for (int i=0;i<arrayLen;i++){
            array[i] = scanner.nextInt();
        }
        int left = 0;
        int right = 0;
        int ret = 0;
        Map<Integer,Integer> map = new HashMap<>();
        while (left<arrayLen&& right<arrayLen){
            int appearCount = map.getOrDefault(array[right],0)+1;
            map.put(array[right],appearCount);
            if (map.get(array[right])>=k){
                ret+=(arrayLen-right);//子数组，右侧只要包含进了right，都是符合条件的

                map.put(array[left],map.get(array[left])-1);
                left++;

                map.put(array[right],map.get(array[right])-1);
                right--;
            }
            right++;
        }
        System.out.println(ret);

    }
}
