package com.lib.od;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目描述
 * 在一行中输入一个字符串数组Q，如果其中一个字符串的所有以索引0开头的子串在数组中都有，
 * 那么这个字符串就是潜在密码在所有潜在密码中最长的是真正的密码，如果有多个长度相同的
 * 真正的密码，那么取字典序Q最大的为唯一的真正的密码，求唯一的真正的密码。
 */
public class 真正的密码 extends BaseTest{
    @Override
    protected void officialSolution() {
        String words = scanner.nextLine();
        List<String> wordList = new ArrayList<>(Arrays.stream(words.split(" ")).collect(Collectors.toList()));
        Collections.sort(wordList, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return (a.length()!=b.length() ? a.length()-b.length() : a.compareTo(b));
            }
        });
        for (int i=wordList.size()-1;i>=0;i--){
            String word = wordList.get(i);
            for (int j =word.length()-1;j>=1;j--){
                if (!wordList.contains(word.substring(0,i))){
                   break;
                }
                if (j == 1){
                    //找到了答案
                    System.out.println(word);
                    return;
                }
            }
        }
    }

    @Override
    protected void mySolution() {

    }
}
