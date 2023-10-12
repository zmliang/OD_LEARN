package com.lib.od;

import java.math.BigInteger;
import java.util.Arrays;

public class 计算数组中心位置 extends BaseTest{

    @Override
    protected void officialSolution() {
        Integer[] array = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        BigInteger fact = BigInteger.valueOf(1);
        for (Integer num:array){
            fact= fact.multiply(BigInteger.valueOf(num));
        }
        BigInteger left = BigInteger.valueOf(1);
        BigInteger right = fact.divide(BigInteger.valueOf(array[0]));
        if (left.compareTo(right) == 0){
            System.out.println(0);
            return;
        }
        for (int i=1;i<array.length;i++){
            left = left.multiply(BigInteger.valueOf(array[i-1]));
            right = right.divide(BigInteger.valueOf(array[i]));
            if (left.compareTo(right) == 0){
                break;
            }
        }

    }

    @Override
    protected void mySolution() {

    }
}
