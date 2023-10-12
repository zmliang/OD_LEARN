package com.lib.od;

import com.sun.org.apache.bcel.internal.classfile.ArrayElementValue;

import java.util.Arrays;

public class 开放日活动取出尽量少的球 extends BaseTest{





    @Override
    protected void officialSolution() {
        int sum = scanner.nextInt();
        int n = scanner.nextInt();

        Integer[] buckets = new Integer[n];
        for (int i=0;i<n;i++){
            buckets[i] = scanner.nextInt();
        }
        //System.out.println(getResult(sum,buckets,n));
    }


    public void mySolution() {
        int sum = 14;
        int[] array = new int[]{2,3,2,5,5,2,4};
        int total = Arrays.stream(array).sum();
        if (total<=sum){
            return;
        }

        int perMaxValue = sum/ array.length;

        int capacity = perMaxValue;

        int[] result = new int[array.length];
        int count;
        while (true){
            int i = 0;
            count = total;
            while (i< array.length){
                int dif = array[i]>capacity ? array[i]-capacity : 0;
                count-=dif;
                result[i] = dif;
                i++;
            }
            if (count>sum){
                capacity++;
            }else {
                break;
            }
        }

        for (int val:result){
            System.out.println(val);
        }

    }
}
