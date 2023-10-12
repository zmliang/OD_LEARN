package com.lib.od;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 题目描述有一组区间[a0，b0]，a1，b11，... (a，b表示起点，终点)，区间有可能重叠、相邻，重叠或相邻则可以合并为更大的区间;给定一组连接器1，x2，x3，...]
 * (x表示连接器的最大可连接长度，即x>=gap)，可用于将分离的区间连接起来，但两个分离区间之能只能使用1个连接器;
 * 请编程实现使用连接器后，最少的区间数结果
 * 区间数量<10000，a,b均 <=10000
 * 连接器梳理<10000;x <= 10000
 * 输入描述
 * 区间组:[1,10],[15,20],[18,30],[33，40]连接器组:[5,4,3,2]
 *
 *
 * 题目解析
 * 华为真的很喜欢考类似的区间合并问题。
 * 1.第一步在不使用连接器的情况下，直接进行区间合并得到结果
 * 2.第二步，上连接器。遍历步骤1的结果，找到分离的区间，计算区间的大小，然后与连接器比较，看看长度是否合适
 * 个区间合并问题的解题思路。首先，将输入的区间按照起点值升序排序，然后进行区间合并。合并的逻辑是，创建一个辅助数组，
 * 将第个区间作为初始值加入该数组，然后遍历剩余的区间，如果该区间可以和辅助数组的栈顶元素合并，则弹出栈顶元素并将合
 * 并后的区间加入数组;如果不能合并，则计算该区间和栈顶元素之间的差值，并将差值加入一个difs数组中，然后将该区间加入辅助数组。
 * 接下来要找到最少的区间数，即尽量让连接器不浪费。首先将difs数组降序排序，然后将连接器数组也降序排序。然后，不断弹出
 * 连接器数组的栈顶元素，即最小长度的连接器，来对比difis数组的栈顶元素，即最短的空隙。如果最小长度的连接器可以满足最短的空隙，
 * 则将diffs栈顶元素弹出，否则不弹出，继续找下一个连接器。最终，区间数等于difs数组长度加1，因为相邻的两个区间之间有一个空隙，
 * 所以空隙个数加1就是区间个数。
 */
public class 连接器问题 extends BaseTest{
    @Override
    protected void officialSolution() {
        String range_str = scanner.next(); // 输入区间字符串
        range_str = range_str.replaceAll("\\[|\\]", ""); // 去掉左右括号
        String[] temp_ranges = range_str.split(","); // 切分区间字符串
        List<int[]> ranges = new ArrayList<>(); // 定义列表ranges存储区间
        for (int i = 0; i < temp_ranges.length; i += 2) { // 遍历切分结果
            int[] single_range = new int[2]; // 定义数组single_range存储单个区间
            single_range[0] = Integer.parseInt(temp_ranges[i]); // 将左端点加入single_range
            single_range[1] = Integer.parseInt(temp_ranges[i+1]); // 将右端点加入single_range
            ranges.add(single_range); // 将single_range加入ranges
        }
        String connector_str = scanner.next(); // 输入区间连接器字符串
        connector_str = connector_str.replaceAll("\\[|\\]", ""); // 去掉左右括号
        String[] temp_connectors = connector_str.split(","); // 切分区间连接器字符串
        List<Integer> connectors = new ArrayList<>(); // 定义列表connectors存储区间连接器
        for (String temp_connector : temp_connectors) { // 遍历切分结果
            connectors.add(Integer.parseInt(temp_connector)); // 将连接器加入connectors
        }

        // 区间合并
        Collections.sort(ranges, new Comparator<int[]>() { // 对区间进行排序
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] > b[0]) { // 如果a区间的左端点大于b区间的左端点
                    return 1; // a排在b后面
                } else if (a[0] == b[0]) { // 如果a区间的左端点等于b区间的左端点
                    if (a[1] > b[1]) { // 如果a区间的右端点大于b区间的右端点
                        return 1; // a排在b后面
                    }
                }
                return -1; // a排在b前面
            }
        });
        List<int[]> merge_ranges = new ArrayList<>(); // 定义列表merge_ranges存储合并后的区间
        merge_ranges.add(ranges.get(0)); // 将第一个区间加入merge_ranges
        List<Integer> range_diffs = new ArrayList<>(); // 定义列表range_diffs存储相邻区间的距离
        for (int i = 1; i < ranges.size(); i++) { // 遍历区间
            int[] range1 = merge_ranges.get(merge_ranges.size() - 1); // 取出merge_ranges中最后一个区间
            int[] range2 = ranges.get(i); // 取出当前区间
            if (range2[0] <= range1[1]) { // 如果当前区间与最后一个区间有交集
                merge_ranges.remove(merge_ranges.size() - 1); // 弹出最后一个区间
                merge_ranges.add(new int[]{range1[0], Math.max(range1[1], range2[1])}); // 将合并后的区间加入merge_ranges
            } else {
                range_diffs.add(range2[0] - range1[1]); // 计算当前区间与最后一个区间的距离并加入range_diffs
                merge_ranges.add(range2); // 将当前区间加入merge_ranges
            }
        }

        // 区间连接器使用
        Collections.sort(range_diffs); // 对区间距离进行排序
        Collections.sort(connectors); // 对区间连接器进行排序
        int result = merge_ranges.size(); // 初始化结果为合并后的区间数
        int idx = 0; // 初始化区间距离的索引为0
        for (int i = 0; i < connectors.size() && idx < range_diffs.size(); i++) { // 遍历区间连接器
            if (connectors.get(i) >= range_diffs.get(idx)) { // 如果当前连接器可以连接相邻区间
                idx++; // 将区间距离索引加1
                result--; // 将结果减1
            }
        }

        // 输出结果
        System.out.println(result); // 输出最终结果
    }

    @Override
    protected void mySolution() {

    }
}
