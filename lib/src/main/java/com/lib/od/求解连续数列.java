package com.lib.od;

import java.util.ArrayList;
import java.util.List;

public class 求解连续数列 extends BaseTest{


    @Override
    protected void officialSolution() {
        int sum = scanner.nextInt();
        int n = scanner.nextInt();
        int left,right;
        if (n%2 == 0){//n是偶数
            int halfLen = n/2-1;
            left = Math.floorDiv(sum,n)-halfLen;
            right = Math.floorDiv(sum,n)+halfLen;
        }else {//奇数
            int mid = sum/n;
            int halfLen = n/2;
            left = mid-halfLen;
            right = mid+halfLen;
        }
        List<Integer> ret = new ArrayList<>();
        for (int i=left;i<=right;i++){
            ret.add(i);
        }
        System.out.println(ret);
    }

    @Override
    protected void mySolution() {

    }
}
