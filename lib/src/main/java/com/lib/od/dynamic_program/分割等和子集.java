package com.lib.od.dynamic_program;

import java.util.Arrays;

public class 分割等和子集 {
    public boolean canPartition(int[] nums){
        int sum = Arrays.stream(nums).sum();
        if (sum%2!=0){
            return false;
        }
        int avg = sum/2;
        for (int i=0;i< nums.length;i++){
            for (int j=0;j< nums.length;j++){

            }
        }
        return false;
    }

}
