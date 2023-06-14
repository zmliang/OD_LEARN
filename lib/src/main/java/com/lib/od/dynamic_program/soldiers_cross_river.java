package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

import java.util.Arrays;


/**
 * 士兵过河，动态规划算法
 */
public class soldiers_cross_river extends BaseTest {

    //保证存活的士兵最多，切过河时间最短


    @Override
    protected void mySolution() {
        int k = scanner.nextInt();
        int t = scanner.nextInt();
        int[] array = new int[k];
        int maxRescue = 0;
        int minTime = Integer.MAX_VALUE;
        for (int i=0;i<k;i++){
            array[i] = scanner.nextInt();
        }

        Arrays.sort(array);//对时间进行排序
        int[] dp = new int[k];//dp[i]  前i个士兵过河所需的最短时间
        //每个士兵过河有三种情况，自己过河，和另一个士兵一起划船过河，和另一个士兵一起坐船，其中一个人划船



        for (int i=0;i<k;i++){
            if (i == 0){
                dp[0] = array[0];
                if (dp[0]>t){
                    System.out.println("0 0");
                    return;
                }
            }else if (i == 1){
                dp[1] = array[1];
            }else {
                int t1 = dp[i-1]+array[i]+array[0];//前i-1个士兵过河的时间+第i个士兵过河的时间和第一个士兵过河的时间
                int t2 = dp[i-2]+array[0]+array[i]+array[1]+array[1];//前i-2个士兵过河的时间+两个士兵一起过河的时间
                dp[i] = Math.min(t1,t2);
            }
            if (dp[i]>t){
                System.out.println(i+" "+dp[i-1]);
                return;
            }
        }


    }
}
