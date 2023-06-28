package com.lib.od.滑动窗口;

import com.lib.od.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class 最小覆盖子串 extends BaseTest {


    //找到s中覆盖t的最小字符串

    @Override
    protected void officialSolution() {
        String s = scanner.nextLine();
        String t = scanner.nextLine();

        Map<Character,Integer> tMap = new HashMap<>();
        for ( char c:t.toCharArray()){
            tMap.put(c,tMap.getOrDefault(c,0)+1);
        }
        int valid = 0;

        int ret = Integer.MAX_VALUE;
        int start = 0;
        Map<Character,Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        while (right<s.length()){
            char c = s.charAt(right);
            right++;
            if (tMap.containsKey(c)){
                window.put(c,window.getOrDefault(c,0)+1);
                if (window.get(c) == tMap.get(c)){
                    valid++;
                }
            }

            while (valid == tMap.size()){
                if (right-left<ret){//更新结果
                    start = left;
                    ret = right-left;
                }
                char d = s.charAt(left);
                left++;
                if (tMap.containsKey(d)){
                    if (window.get(d) == tMap.get(c)){
                        valid--;
                    }
                    window.put(d,window.get(d)-1);

                }

            }
        }

    }

    @Override
    protected void mySolution() {

    }
}
