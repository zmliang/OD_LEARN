package com.lib.od;

import java.util.Arrays;

public class 日志限流 extends BaseTest{

    public int getLimit(int[] logs,int maxNum){
        int total = Arrays.stream(logs).sum();
        if (total<=maxNum){
            return -1;
        }
        int len = logs.length;
        int max = Arrays.stream(logs).max().getAsInt();
        int min = maxNum/ len;
        int ans = min;
        while (max>=min){
            int mid = (max+min)/2;
            int tmpTotal = 0;
            for (int log:logs){
                tmpTotal+=Math.min(log,mid);
            }
            if (tmpTotal>maxNum){
                max = mid;
            }else if (tmpTotal<maxNum){
                min = mid;
                ans = mid;
            }else {
                return mid;
            }
        }
        return ans;
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
