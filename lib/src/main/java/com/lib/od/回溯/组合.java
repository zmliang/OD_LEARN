package com.lib.od.回溯;

import com.lib.od.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class 组合 extends BaseTest {
    List<List<Integer>> result = new ArrayList<>();
    public void officialSolution() {

        int n = 4;
        int k=2;
        backtracking(n,k,1,new ArrayList<>());
        System.out.println(result);
    }

    public void backtracking(int n,int k,int index,List<Integer> tmp){
        if (tmp.size() == k){
            result.add(new ArrayList<>(tmp));
            return;
        }
        for (int i=index;i<=n;i++){
            if (tmp.contains(i)){
                continue;
            }
            tmp.add(i);
            backtracking(n,k,i+1,tmp);
            tmp.remove(tmp.size()-1);
        }
    }

    @Override
    protected void mySolution() {

    }
}
