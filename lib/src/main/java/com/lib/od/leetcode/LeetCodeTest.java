package com.lib.od.leetcode;

import com.lib.od.链表.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeetCodeTest {

    /**
     * 合并区间
     */
    public int[][] merge(int[][] intervals){
        Arrays.sort(intervals, (a, b) -> a[0]-b[0]);
        if (intervals.length == 1){
            return intervals;
        }
       List<int[]> ret = new ArrayList<>();
        for (int i=0;i<intervals.length;i++){
            int[] cur = intervals[i];
            int[] last = ret.get(ret.size()-1);
            if (last == null || last[1]<cur[0]){
                ret.add(cur);
            }else {
                last[1] = Math.max(cur[1],last[1]);
            }
        }

        return ret.toArray(new int[ret.size()][]);
    }

    /**
     * 链表排序
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null){
            return head;
        }
        return head;
    }

    public String largestNumber(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i=0;i< nums.length;i++){
            list.add(nums[i]);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return com(a,b);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int val:list){
            sb.append(val);
        }

        return sb.toString();
    }

    int com(int a,int b){
        String str1 = String.valueOf(a);
        String str2 = String.valueOf(b);

        if (Long.parseLong(str1+str2)>Long.parseLong(str2+str1)){
            return -1;
        }
        return 1;
    }


    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;

        int[] result = new int[]{-1,-1};
        int index = -1;
        while (start<=end){
            int mid = (end-start)/2;
            if (nums[mid] == target){
                index = mid;
                break;
            }else if (nums[mid]>target){
                end = mid-1;
            }else {
                start = mid+1;
            }
        }
        if (index == -1){
            return result;
        }

        int left = index;
        int right = index;
        while (nums[left-1] == target){
            left--;
        }
        while (nums[right+1] == target){
            right++;
        }
        result[0] = left;
        result[1] = right;
        return result;
    }


    /**
     * 模拟行走机器人
     * @param commands
     * @param obstacles
     * @return
     */

    public int robotSim(int[] commands, int[][] obstacles) {
        //方向数组，分别存储向北、向东、向南、向西(顺时针方向，刚好与题目所示的相同)
        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int d = 0; //记录机器人的方向
        int curX = 0, curY = 0; //表示当前所在的下标
        int ans = 0; //记录最大的欧氏距离平方
        Set<String> obstaclesSet = new HashSet<>(); //存储障碍物，这里用字符串存储，方便对障碍物的x坐标和y坐标进行判断
        for (int i = 0; i < obstacles.length; i++) {
            obstaclesSet.add(obstacles[i][0] + "," + obstacles[i][1]); //通过逗号将x和y坐标分隔
        }
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == -2) { //向左转90度，这里通过取模来实现循环操作
                d = (d + 3) % 4;
            }else if (commands[i] == -1) { //向右转90度
                d = (d + 1) % 4;
            }else {
                for (int j = 1; j <= commands[i]; j++) { //每走一步，判断一步
                    int nextX = curX + dir[d][0];
                    int nextY = curY + dir[d][1];
                    if (obstaclesSet.contains(nextX + "," + nextY)) { //走的下一步会遇到障碍物，则退出循环，进行下一条命令
                        break;
                    }
                    curX = nextX; //更新当前的坐标
                    curY = nextY;
                }
                ans = Math.max(ans, curX * curX + curY * curY); //每条命令执行完后，更新最大值
            }
        }
        return ans;
    }




}
