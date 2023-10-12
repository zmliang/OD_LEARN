package com.lib.od;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 题目描述
 * 给定两个数组a，b，若al == blj 则称 [i,j] 为一个二元组，求在给定的两个数组中，二元组的个数
 * 输入描述
 * 第一行输入 m
 * 第二行输入m个数，表示第一个数组
 * 第三行输入 n
 * 第四行输入n个数，表示第二个数组
 *
 * 题目解析
 * 简单题，用双重for循环，就可以得到结果，复杂度是O(n*m)。但是在如果数组的数量特别大的话，可能会超时可以采用空间换时间。定义一个字典存储元素以及元素出现的次数。
 */
public class 二元组个数 extends BaseTest{
    @Override
    protected void officialSolution() {
        int m = scanner.nextInt(); // 输入m的值
        List<Integer> listM = new ArrayList<>(m); // 创建一个长度为m的整型列表listM
        for (int i = 0; i < m; i++) { // 循环m次
            listM.add(scanner.nextInt()); // 逐个输入listM中的元素值
        }

        int n = scanner.nextInt(); // 输入n的值
        List<Integer> listN = new ArrayList<>(n); // 创建一个长度为n的整型列表listN
        for (int i = 0; i < n; i++) { // 循环n次
            listN.add(scanner.nextInt()); // 逐个输入listN中的元素值
        }

        Set<Integer> setM = new HashSet<>(listM); // 创建一个无序集合setM，并将listM中的元素作为参数传入
        Set<Integer> setN = new HashSet<>(listN); // 创建一个无序集合setN，并将listN中的元素作为参数传入

        Map<Integer, Integer> countM = new HashMap<>(); // 创建一个无序映射countM
        for (int m2 : listM) { // 循环listM中的元素
            if (setN.contains(m2)) { // 如果setN中包含该元素
                countM.put(m2, countM.getOrDefault(m2, 0) + 1); // 将该元素作为键，值加1存入countM
            }
        }

        Map<Integer, Integer> countN = new HashMap<>(); // 创建一个无序映射countN
        for (int n2 : listN) { // 循环listN中的元素
            if (setM.contains(n2)) { // 如果setM中包含该元素
                countN.put(n2, countN.getOrDefault(n2, 0) + 1); // 将该元素作为键，值加1存入countN
            }
        }

        int count = 0; // 创建一个整型变量count，初始值为0
        for (Map.Entry<Integer, Integer> entry : countM.entrySet()) { // 循环countM中的键值对
            int k = entry.getKey();
            int v = entry.getValue();
            count += v * countN.getOrDefault(k, 0); // 将countM中该键对应的值与countN中该键对应的值相乘，并加入count中
        }

        System.out.println(count); // 输出count的值并换行

    }

    @Override
    protected void mySolution() {

    }
}
