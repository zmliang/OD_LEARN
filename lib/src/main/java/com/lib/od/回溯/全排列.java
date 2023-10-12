package com.lib.od.回溯;

import com.lib.od.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 全排列 extends BaseTest {
    List<List<Integer>> result = new ArrayList<>();

    public void permute(int[] nums){

        List<Integer> current = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        Arrays.fill(visited,false);
        backtrack(nums,visited,current,0);

        System.out.println(result);
    }

    private void backtrack(int[] nums,boolean[] visited,List<Integer> current,int start){
        if (current.size() == nums.length){
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i=0;i< nums.length;i++){
            if (visited[i]){
                continue;
            }
            visited[i] = true;
            current.add(nums[i]);
            backtrack(nums, visited, current,i+1);
            current.remove(current.size()-1);
            visited[i] = false;
        }
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
