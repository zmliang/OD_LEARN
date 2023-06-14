package com.lib.od;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 题目描述
 * 给一个无向图Q染色，可以填红黑两种颜色，必须保证相邻两个节点不能同时为红色，输出有多少种不同的染色方案?
 * 输入描述
 * 第一行输入M(图中节点数)N(边数)
 * 后续N行格式为: V1 V2表示一个V1到V2的边
 * 数据范围: 1 <= M <= 15,0 <= N <= M* 3，不能保证所有节点都是连通的.
 * 输出描述
 * 输出一个数字表示染色方案的个数
 * 说明
 * 0<n<15
 * 0<=m <=n*3
 * 0 <= s.t< n
 * 不保证图连通
 * 保证没有重边和自环
 *
 * 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
public class 无向图染色 extends BaseTest{
    /**
     * 深度优先搜索
     *
     * @param connect 节点与其相邻的节点
     * @param nodeNum 节点数
     * @param index 当前节点
     * @param count 染色方案数
     * @param path 已访问的节点
     * @return 染色方案数
     */
    public static int dfs(
            HashMap<Integer, HashSet<Integer>> connect,
            int nodeNum,
            int index,
            int count,
            LinkedList<HashSet<Integer>> path) {

        // 所有节点都已访问，返回染色方案数
        if (path.size() == nodeNum) {
            return count;
        }

        // 遍历所有节点
        outer:
        for (int i = index; i <= nodeNum; i++) {
            // 如果该节点已经访问过，则跳过
            for (HashSet<Integer> p : path) {
                if (p.contains(i)) {
                    continue outer;
                }
            }

            // 将节点染色
            count++;

            // 如果该节点有相邻节点，则将相邻节点加入 path 中
            if (connect.containsKey(i)) {
                path.addLast(connect.get(i));
                count = dfs(connect, nodeNum, i + 1, count, path);
                path.removeLast();
            } else {
                count = dfs(connect, nodeNum, i + 1, count, path);
            }
        }

        return count;
    }
    @Override
    void officialSolution() {

        int nodeNum = scanner.nextInt();
        int edgeNum = scanner.nextInt();

        int[][] edges = new int[edgeNum][2];
        for (int i = 0; i < edgeNum; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        // 将节点与其相邻的节点存储在 HashMap 中
        HashMap<Integer, HashSet<Integer>> connect = new HashMap<>();
        for (int[] edge : edges) {
            connect.putIfAbsent(edge[0], new HashSet<>());
            connect.get(edge[0]).add(edge[1]);

            connect.putIfAbsent(edge[1], new HashSet<>());
            connect.get(edge[1]).add(edge[0]);
        }

        // 从第一个节点开始深度优先搜索
        System.out.println(dfs(connect, nodeNum, 1, 1, new LinkedList<>()));

    }

    @Override
    protected void mySolution() {

    }
}
