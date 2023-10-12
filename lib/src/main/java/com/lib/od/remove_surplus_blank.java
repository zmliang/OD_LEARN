package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 华为OD机试 去除多余空格
 */
public class remove_surplus_blank extends BaseTest{


    @Override
    protected void officialSolution() {
        String text = scanner.nextLine(); // 输入文本
        Integer[][] ranges = Arrays.stream(scanner.nextLine().split(",")) // 输入关键词的起始和结束坐标
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new))
                .toArray(Integer[][]::new);

        getResult(text, ranges);
    }

    private void getResult(String text, Integer[][] ranges) {
        boolean inQuote = false; // 是否在引号内
        ArrayList<Integer> spacesToRemove = new ArrayList<>(); // 需要删除的空格下标

        for (int i = 0; i < text.length(); i++) {
            if (i > 0 && text.charAt(i) == ' ' && text.charAt(i - 1) == ' ' && !inQuote) {
                // 如果当前字符是空格，前一个字符也是空格，且不在引号内，则需要删除
                spacesToRemove.add(i);
            }

            if (text.charAt(i) == '\'') { // 如果当前字符是引号
                inQuote = !inQuote; // 切换标志
            }
        }

        char[] textCharArray = text.toCharArray();
        Integer[][] ans = Arrays.stream(ranges).map(Integer[]::clone).toArray(Integer[][]::new);

        for (Integer spaceToRemove : spacesToRemove) {
            textCharArray[spaceToRemove] = '\u0000'; // 将空格替换为空字符
            for (int i = 0; i < ranges.length; i++) {
                int start = ranges[i][0];
                if (spaceToRemove < start) {
                    ans[i][0]--; // 区间起始位置减一
                    ans[i][1]--; // 区间结束位置减一
                }
            }
        }

        System.out.println(new String(textCharArray).replace("\u0000", "")); // 输出处理后的字符串

        StringBuilder sb = new StringBuilder();
        for (Integer[] an : ans) {
            sb.append(Arrays.toString(an)); // 将区间转换为字符串并添加到 StringBuilder 中
        }
        System.out.println(sb); // 输出区间字符串
    }


    @Override
    protected void mySolution() {

    }
}
