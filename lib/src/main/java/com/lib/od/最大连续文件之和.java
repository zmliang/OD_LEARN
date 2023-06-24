package com.lib.od;

public class 最大连续文件之和 extends BaseTest{

    public int getResult(int max,int[] values){
        int left=0 , right=0;
        int winValue = 0;
        int ret = 0;
        while (right<values.length){
            winValue+=values[right];
            if (winValue>max){
                winValue-=values[left];
                left++;
            }
            ret = Math.max(ret,winValue);
            right++;
        }
        return ret;
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
