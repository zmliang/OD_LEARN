package com.lib.od.贪心;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class root {

    public void test(){
        jump(new int[]{2,3,1,2,4,2,3});
    }


    public int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPos = 0;//目前能跳到到最远位置
        int steps = 0;
        for(int i=0;i<length-1;i++){
            maxPos = Math.max(maxPos,i+nums[i]);
            if(i == end){//到达上次跳跃的最远位置了
                end = maxPos;
                steps++;
            }
        }
        return steps;
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        List<List<Integer>> ret = new ArrayList<>();
        dfs(nums,new ArrayList<>(),ret,used,new HashSet<>());
        return ret;
    }


    private void dfs(int[] list, List<Integer> path, List<List<Integer>> result, boolean[] used, Set<Integer> set){
        if (path.size() == list.length){
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i=0;i<list.length;i++){
            if (used[i]){
                continue;
            }
            int value = list[i];
            if (set.contains(value)){
                continue;
            }
            set.add(value);
            used[i] = true;
            path.add(value);
            dfs(list,path,result,used,set);
            used[i] = false;
            set.remove(value);
            path.remove(path.size()-1);
        }
    }



    public void rotate(int[][] matrix) {

    }

}
