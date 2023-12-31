package com.lib.od;

import java.util.Arrays;

/**
 * 题目描述
 * 部门组织绿岛骑行团建活动。租用公共双人自行车，每辆自行车最多坐两人，最大载重M。
 * 给出部门每个人的体重，请问最多需要租用多少双人自行车。
 * 输入描述
 * 第一行两个数字m、n，分别代表自行车限重，部门总人数.
 * 第二行，n个数字，代表每个人的体重，体重都小于等于自行车限重m。
 * 。0<M<=200
 * 。0<n<=1000000
 * 输出描述
 * 最小需要的双人自行车数量。
 *
 * 题目解析
 * 本题的条件:
 * 。一辆车最多2个人
 * 。人体重都小于等于自行车限重m
 * 本题需要最少的车辆，即尽可能组合出重量小于等于m的两人组。按照体重升序排序，利用双指针，
 * 一个在头，一个在尾部，如果两个体重加起来大于车的载重，那么表示尾部的人太重了。他只能自己骑。
 * 如果小于载重，说明有可以一起骑车。
 *
 * 代码思路
 * 这道题目是一道贪心算法的题目，需要将体重从小到大排序，然后使用双指针的方式进行配对。
 * 具体的做法是，将体重最轻的人和体重最重的人配对，如果他们的体重之和小于等于自行车的限重，
 * 则这两个人可以共乘一辆自行车，否则只能让体重最重的人单独骑一辆自行车。然后再将体重次轻的
 * 人和体重次重的人配对，以此类推，直到所有人都被配对。每次配对需要租用一辆自行车，最后未配对
 * 的人需要额外租用一辆自行车。最终需要租用的自行车数量就是所需的最小自行车数量。
 * 
 */
public class 租车骑绿岛 extends BaseTest{

    @Override
    protected void officialSolution() {
        int maxWeight = scanner.nextInt();
        int n = scanner.nextInt();
        // 输入每个人的体重，存入数组中
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }
        // 将体重从小到大排序
        Arrays.sort(weights);
        // 初始化所需自行车数量为0，i指向体重最小的人，j指向体重最大的人
        int bikeCount = 0, i = 0, j = n - 1;
        // 当i小于j时，说明还有人未配对
        while (i < j) {
            // 如果i和j的体重之和小于等于自行车限重，则i和j配对
            if (weights[i] + weights[j] <= maxWeight) {
                i++;
            }
            // 无论i和j是否配对，j都要向前移动
            j--;
            // 每次循环都需要租用一辆自行车
            bikeCount++;
        }
        // 当i等于j时，说明还有一人未配对，需要额外租用一辆自行车
        if (i == j) {
            bikeCount++;
        }
        // 输出所需自行车数量
        System.out.println(bikeCount);
    }

    @Override
    protected void mySolution() {

    }
}
