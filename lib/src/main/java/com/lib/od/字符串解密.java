package com.lib.od;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * 题目描述
 * 给定两个字符串string1和string2
 * string1是一个被加扰的字符串。
 * string1由小写英文字母 ('a'z)和数字字符 (o”9) 组成，而加扰字符串由'0'g、af组成。
 * string1里面可能包含0个或多个加扰子串，剩下可能有0个或多个有效子串，这些有效子串被加扰子串隔开。
 * string2是一个参考字符串，仅由小写英文字母 (a'~'z) 组成
 * 你需要在string1字符串里找到一个有效子串，这个有效子串要同时满足下面两个条件:
 * (1)这个有效子串里不同字母的数量不超过且最接近于string2里不同字母的数量，即小于或等于string2里不同字母的数量的同时且最大
 * (2) 这个有效子串是满足条件 (1)里的所有子串 (如果有多个的话)里 字典序Q最大的一个。
 * 如果没有找到合适条件的子串的话，请输出”Not Found”
 *
 *
 *
 * 代码思路
 * 1.读入输入字符串inputString1和inputString2，以及用于匹配加扰子串的正则表达式regex=[0-9a-月+”
 * 2.将inputString1按照正则表达式regex进行分割，得到一个字符串数组validSubstrings，其中每个元素为一个有效子串或一个加扰子串
 * 3.对每个有效子串进行筛选，只保留不为空且不同字母数量小于等于inputString2不同字母数量的子串，得到一个新的字符串数组filteredSubstrings
 * 4.如果filteredSubstrings为空，则输出"Not Found"，否则继续执行
 * 5.对filteredSubstrings进行排序，排序规则如下:
 * a.按照子串中不同字母的数量从大到小排序
 * b.如果不同字母的数量相同，则按照字典序从大到小排序
 * 6.输出排序后的filteredSubstrings数组的第一个元素，即为符合条件的子串
 */
public class 字符串解密 extends BaseTest{

    /**
     * 获取字符串中不同字母的数量
     * @param str
     * @return
     */
    public static int getDistinctCount(String str){
        HashSet<Character> set = new HashSet<>();
        for (char c:str.toCharArray()){
            set.add(c);
        }
        return set.size();
    }

    @Override
    protected void officialSolution() {
        String str1 = scanner.next();
        String str2 = scanner.next();
        String regEx = "[0-9a-f]+";
        String[] validSubStr = str1.split(regEx);
        int distinctCount = getDistinctCount(str2);

        String[] filteredSubstr = Arrays.stream(validSubStr)
                .filter(valid->!"".equals(valid) && getDistinctCount(valid)<=distinctCount)
                .toArray(String[]::new);

        if (filteredSubstr.length == 0){
            System.out.println("Not Found");
            return;
        }
        Arrays.sort(filteredSubstr,(a,b)->{
            int count1 = getDistinctCount(a);
            int count2 = getDistinctCount(b);
            return count1!=count2 ? count2-count1 : b.compareTo(a);
        });

        System.out.println(filteredSubstr[0]);
    }

    @Override
    protected void mySolution() {
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();

        String[] validStrs = s1.split("[0-9a-f]+");

        Map<Character,Integer> s2Map=new HashMap<>();
        for (char c:s2.toCharArray()){
            s2Map.put(c,s2Map.getOrDefault(c,0)+1);
        }
        int difCount = s2Map.size();


    }
}



















