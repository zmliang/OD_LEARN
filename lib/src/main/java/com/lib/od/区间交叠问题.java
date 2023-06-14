package com.lib.od;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 题目描述给定坐标轴上的一组线段，线段的起点和终点均为整数并且长度不小于1，请你从中找到最少数量的线段，这些线段可以覆盖柱所有线爱
 * 输入描述
 * 第一行输入为所有线段的数量，不超过10000，后面每行表示一条线段，格式为",y，x和y分别表示起点和终点，取值范围是下105.105]
 * 输出描述
 * 最少线段数量，为正整数
 *=================================
 * 典型的 区间合并Q题，大家可以参考:
 * 56.合并区间 - 力扣 (LeetCode)
 * 1.先将所有的线段按照起点排序
 * 2.循环选取每一个线段。将当前线段作为起始线段。然后找到所有剩余线段起点 小于等于 当前其实线段的结束点，
 * 这些剩余的线段就在当前线段中。然后在这些线段中，找到结束点值最大的线段。直到覆盖完整个长度为m的区间，就能得到最少的线段数.
 *
 * 代码思路
 * 该代码的解题思路如下:
 * 1.输入所有线段的数量和每条线段的起点和终点，存储到一个二维向量 segments 中。
 * 2.对 segments 按照线段起点进行排序。
 * 3.使用栈来合并线段。定义一个二维向量 stack，用于存储合并后的线段。将 segments 的第一个线段存入 stack 中.
 * 4.遍历 seoments 中的每个线段，取出线段的起点和终点，与 stack 中最后一个线段进行比较。如果当前线段与最后一个线段不能合并
 * ，则将当前线段存入 stack 中;否则，更新最后一个线段的终点或弹出最后一个线段。5.最后输出 stack 的长度即为最少线段数量,
 * 其中，使用栈来合并线段的思路是:将线段按照起点排序，然后依次遍历每个线段，将线段与栈顶的线段进行比较。如果当前线段的起点在
 * 栈顶线段的范围内，则更新栈顶线段的终点;否则，将当前线段压入栈中。这样，最后栈中存储的线段就是最少数量的线段，可以覆盖所有线段。
 */
public class 区间交叠问题 extends BaseTest{

    @Override
    void officialSolution() {
        int n = Integer.parseInt(scanner.nextLine());

        Integer[][] ranges = new Integer[n][];
        for (int i = 0; i < n; i++) {
            // 将输入的字符串按逗号分隔，转换成整数数组
            ranges[i] = Arrays.stream(scanner.nextLine().split(","))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }

        // 按照线段起点从小到大排序
        Arrays.sort(ranges, (a, b) -> a[0] - b[0]);

        LinkedList<Integer[]> stack = new LinkedList<>();
        stack.add(ranges[0]);

        for (int i = 1; i < ranges.length; i++) {
            Integer[] range = ranges[i];

            while (true) {
                if (stack.isEmpty()) {
                    stack.add(range);
                    break;
                }

                Integer[] top = stack.getLast();
                int start0 = top[0];
                int end0 = top[1];

                int start1 = range[0];
                int end1 = range[1];

                if (start1 <= start0) {
                    if (end1 <= start0) {
                        break;
                    } else if (end1 < end0) {
                        break;
                    } else {
                        stack.removeLast();
                    }
                } else if (start1 < end0) {
                    if (end1 <= end0) {
                        break;
                    } else {
                        stack.add(new Integer[]{end0, end1});
                        break;
                    }
                } else {
                    stack.add(range);
                    break;
                }
            }
        }
        System.out.println(stack.size());

    }

    @Override
    protected void mySolution() {

    }
}
