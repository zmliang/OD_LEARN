package com.lib.od.滑动窗口;

import com.lib.od.BaseTest;

public class 长度最小的子数组 extends BaseTest {


    @Override
    protected void officialSolution() {
        int[] array = new int[]{2,3,1,2,4,3};
        int k = 7;
        int left=0;
        int right=1;
        int sum = array[0];

        int minLen = Integer.MAX_VALUE;

        while (right< array.length){
            sum+=array[right];
            right++;

            while (sum>=7){
                minLen = Math.min(minLen,right-left);
                sum-=array[left];
                left++;
            }
        }
    }

    @Override
    protected void mySolution() {

    }
}
