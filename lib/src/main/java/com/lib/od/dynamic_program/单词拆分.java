package com.lib.od.dynamic_program;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class 单词拆分 {
    private Set<String> wordSet = new HashSet<>();
    {
        wordSet.add("leet");
        wordSet.add("code");
    }

    public boolean wordBreak(String str){
        boolean[] dp = new boolean[str.length()];
        dp[0] = true;//空字符串能在set里找到
        for (int i=1;i<=str.length();i++){
            for (int j=0;j<i;j++){
                String sub = str.substring(j,i);
                if (dp[j] && wordSet.contains(sub)){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[str.length()];
    }
}
