package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

public class 编辑距离 extends BaseTest {
    @Override
    protected void officialSolution() {

        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();

        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1+1][len2+1];//字符串str1的前i个转换澄str2的前j个需要的最小操作

        for (int i=0;i<len1;i++){
            dp[i][0] = i;
        }

        for (int i=0;i<len1;i++){
            dp[0][i] = i;
        }

        for (int i=1;i<=len1;i++){
            for (int j=1;j<=len2;j++){
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = Math.min(dp[i-1][j-1],Math.min(dp[i-1][j],dp[i][j-1]))+1;
                }
            }
        }

        System.out.println(dp[len1][len2]);

    }

    @Override
    protected void mySolution() {

    }
}
