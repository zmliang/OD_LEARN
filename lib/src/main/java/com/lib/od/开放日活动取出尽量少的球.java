package com.lib.od;

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

//    public static String getResult(int sum,Integer[] buckets,int n){
//
//    }

    @Override
    protected void mySolution() {

    }
}
