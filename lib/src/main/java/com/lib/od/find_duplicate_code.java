package com.lib.od;

import java.util.Scanner;

public class find_duplicate_code extends BaseTest{


    //动态规划算法

    /**
     * 动态规划的核心是穷举
     *
     *
     */

    private String findLongestCommonSubstring(String s1,String s2){
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1+1][len2+1];

        int end = 0;
        int maxLen = 0;

        for (int i=1;i<=len1;i++){
            for (int j=1;j<=len2;j++){
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                    if (dp[i][j]>maxLen){
                        end = i;
                        maxLen = dp[i][j];
                    }
                }else {
                    dp[i][j] = 0;
                }
            }
        }
        if (maxLen == 0){
            return "";
        }
        return s1.substring(end-maxLen,end);
    }

    @Override
    protected void mySolution() {
        String code1 = scanner.nextLine();
        String code2 = scanner.nextLine();

        if (code1.contains(code2)){
            System.out.println(code2);
            return;
        }
        if (code2.contains(code1)){
            System.out.println(code1);
            return;
        }

        String ret = findLongestCommonSubstring(code1,code2);
        System.out.println(ret);

    }
}
