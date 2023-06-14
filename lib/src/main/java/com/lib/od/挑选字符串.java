package com.lib.od;


import java.util.HashMap;
import java.util.Map;

/**
 * https://blog.csdn.net/banxia_frontend/article/details/129460841
 *
 * <p>给定a-z，26个英文字母小写字符串组成的字符串A和B，其中A可能存在重复字母，B不会存在重复字母，现从字符串A中按规则挑选一些字母可以组成字符串B。</p>
 * <li>同一个位置的字母只能挑选一次，</li>
 * <li>被挑选字母的相对先后顺序不能被改变，</li>
 * <li>求最多可以同时从A中挑选多少组能组成B的字符串
 *
 *这道题是一道字符串匹配问题。首先需要记录字符串B中每个字符的位置，
 * 然后遍历字符串A，如果遇到字符串B中的字符，就更新能够匹配到该字符
 * 的数量。具体实现中，首先记录字符在字符串B中的位置，使用一个数组来
 * 记录能够匹配到字符串B中字符的数量。遍历字符串A时，如果遇到字符串
 * B中的字符，就根据该字符在字符串B中的位置和前面的字符匹配情况来更
 * 新数组中的值。最后输出数组中最后一个值即可。
 *
 *
 */
public class 挑选字符串 extends BaseTest{




    @Override
    void officialSolution() {
        String strA = scanner.next();
        String strB = scanner.next();

        // 记录字符串B中每个字符的位置
        Map<Character, Integer> charIndex = new HashMap<>();
        for (int i = 0; i < strB.length(); i++) {
            charIndex.put(strB.charAt(i), i);
        }

        // 记录字符串A中能够匹配到字符串B中字符的数量
        int[] matchCount = new int[strB.length()];
        for (int i = 0; i < strA.length(); i++) {
            char c = strA.charAt(i);
            if (charIndex.containsKey(c)) { // 如果字符串B中存在字符c
                int index = charIndex.get(c); // 获取字符c在字符串B中的位置
                if (index == 0 || matchCount[index] < matchCount[index - 1]) {
                    // 如果字符c在字符串B中的位置是0或者字符c前面的位置可以匹配到更多的字符
                    matchCount[index]++;
                }
            }
        }

        // 输出匹配到字符串B中所有字符的数量
        System.out.println(matchCount[strB.length() - 1]);
    }

    @Override
    protected void mySolution() {

    }
}
