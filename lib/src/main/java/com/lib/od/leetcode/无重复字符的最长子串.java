package com.lib.od.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class 无重复字符的最长子串 {

    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        int ret = Integer.MIN_VALUE;
        while (right<s.length()){
            char c = s.charAt(right);
            while (set.contains(c) && left<right){
                set.remove(s.charAt(left++));
            }
            set.add(c);
            ret = Math.max(ret,right-left+1);
        }
        return ret;
    }

//    public List<Integer> findSubstring(String s, String[] words) {
//        if (words.length == 0){
//            return new ArrayList<>();
//        }
//        int size = words.length;
//        int step = words[0].length();
//
//        int left = 0;
//        int right = step;
//        int count = 0;
//        List<String> list = Arrays.stream(words).collect(Collectors.toList());
//        Set<String> set = new HashSet<>();
//        while (left<right && right<s.length()){
//            String sub = s.substring(left,right);
//            if (list.contains(sub)){
//                if (!set.contains(sub)){
//                    set.add(sub);
//                    count++;
//                }
//            }
//
//        }
//
//    }

}
