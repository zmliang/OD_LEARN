package com.lib.od;

public class 微服务的集成测试 extends BaseTest{
    int[] cache;

    public int dfs(int[][] matrix,int k){
        if (cache[k]!=-1){
            return cache[k];
        }
        int[] deployService = matrix[k];
        int maxTime = 0;
        for (int i=0;i<deployService.length;i++){
            if (i!=k && deployService[i]!=0){
                maxTime = Math.max(maxTime,dfs(matrix,i));
            }
        }
        cache[k] = maxTime+matrix[k][k];
        return cache[k];
    }



    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
