package com.lib.od.dynamic_program;

public class 最长回文子串 {

    public String longestPalindrome(String s) {

        if(s.length() == 1){
            return s;
        }
        if(s.length() == 2){
            if(s.charAt(0) == s.charAt(1)){
                return s;
            }
            return ""+s.charAt(0);
        }

        boolean[][] dp = new boolean[s.length()][s.length()];

        int n = s.length();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = true;
            }
        }
        int result = 0;
        int start = 0;

        for(int len = 2;len<=n;len++){//枚举子串长度
            for(int i=0;i<n;i++){//确定左右边界

                int right = len+i-1;
                int left = i;
                if(right>=n){
                    break;
                }
                if(s.charAt(left)!=s.charAt(right)){
                    dp[left][right] = false;
                }else{
                    if(right-left<3){
                        dp[left][right] = true;
                    }else{
                        dp[left][right] = dp[left+1][right-1];
                    }
                }
                if(dp[left][right] && right-left>result){
                    start = left;
                    result = right-left;
                }

            }
        }
        return s.substring(start,start+result+1);

    }

}
