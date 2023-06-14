package com.lib.od;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给定一个m x n的矩阵，
 *
 *
 * 本题可以使用深度优先搜索来解题
 * 首先，我们可以遍历矩阵元素，当遍历到“O”时，已该“O”的坐标位置开始向其上、下、左、右方向开始 深度优先搜索Q
 * ，每搜索到一个“O”，则该空闲区域数量+1，如果搜索到的“O”的坐标位置处于矩阵第一列，或最后一列，或者第一行，或者最后一行，那么该“O”位置就是空闲区域的入口位置，我们将其缓存到out数组中
 * 当所有深度优先搜索的分支都搜索完了，则判断out统计的入口数量,
 * 1.如果只有1个，则该空闲区域是符合题意得单入口空闲区域，输出入口坐标位置，以及空闲区域数量。
 * 2.如果有多个，则该区域不符合要求
 * 代码思路
 * 这道题目可以使用DFS进行求解。首先，对于每个未访问过的空闲位置进行DFS搜索，找到该位置所在的连通块，并将该
 * 连通块的入口加入入口集合。在DFS搜索过程中，需要判断当前位置是否合法.是否已经访问过、是否是占据的位置，并
 * 将已经访问过的位置加入已访问集合中。如果该位置在边界上，则将其加入入口集合中。在DFS搜索结束后，如果入口集
 * 合只有一个元素，则将该连通块的入口和大小加入答案集合中。
 * 最后，对于答案集合中的连通块，按照连通块大小 从大到小排序Q，如果只有一个符合条件的连通块或者最大的连通块大
 * 小比次大的连通块大小大，则输出最大连通块的入口和大小，否则只输出最大连通块的大小。如果没有符合条件的连通块，则输出NULL。
 */
public class find_single_entry_free_area extends BaseTest{
    static int rows, cols; // 矩阵的行数和列数
    static String[][] matrix = new String[200][200]; // 存储矩阵
    static int[][] offset = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 上下左右四个方向的偏移量
    static Set<String> checked = new HashSet<>(); // 存储已经访问过的位置

    // DFS搜索连通块
    public static int dfs(int i, int j, int count, List<List<Integer>> enter) {
        String pos = i + "-" + j; // 将位置转化为字符串

        // 如果位置不合法或者已经访问过或者是占据的位置，则退出搜索
        if (i < 0 || i >= rows || j < 0 || j >= cols || matrix[i][j].equals("X") || checked.contains(pos)) {
            return count;
        }

        checked.add(pos); // 将该位置标记为已访问

        // 如果该位置在边界上，则将其加入入口集合
        if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) enter.add(Arrays.asList(i, j));

        count++; // 连通块大小加1

        // 在上下左右四个方向上继续搜索
        for (int k = 0; k < 4; k++) {
            int offsetX = offset[k][0];
            int offsetY = offset[k][1];

            int newI = i + offsetX;
            int newJ = j + offsetY;
            count = dfs(newI, newJ, count, enter);
        }

        return count;
    }


    @Override
    protected void mySolution() {
        rows = scanner.nextInt();
        cols = scanner.nextInt();

        // 读入矩阵
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.next();
            }
        }

        List<List<Integer>> ans = new ArrayList<>(); // 存储符合条件的连通块的入口和大小

        // 对每个未访问过的空闲位置进行DFS搜索
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j].equals("O") && !checked.contains(i + "-" + j)) {
                    List<List<Integer>> enter = new ArrayList<>();
                    int count = dfs(i, j, 0, enter);
                    if (enter.size() == 1) { // 如果入口只有一个，则将其加入答案集合
                        List<Integer> pos = enter.get(0);
                        ans.add(Arrays.asList(pos.get(0), pos.get(1), count));
                    }
                }
            }
        }

        if (ans.size() == 0) { // 如果没有符合条件的连通块，则输出NULL
            System.out.println("NULL");
            return;
        }

        // 按照连通块大小从大到小排序
        ans.sort((a, b) -> b.get(2) - a.get(2));

        if (ans.size() == 1 || ans.get(0).get(2) > ans.get(1).get(2)) { // 如果只有一个符合条件的连通块或者最大的连通块大小比次大的连通块大小大，则输出最大连通块的入口和大小
            for (int i = 0; i < ans.get(0).size(); i++) {
                System.out.print(ans.get(0).get(i) + " ");
            }
            System.out.println();
        } else { // 否则只输出最大连通块的大小
            System.out.println(ans.get(0).get(2));
        }
    }



}
