package com.lib.od;


import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/banxia_frontend/article/details/129469498
 */
public class 查找树中元素 extends BaseTest{


    @Override
    void officialSolution() {

        // 读取输入的第一行，表示节点信息的行数
        int size = Integer.parseInt(scanner.nextLine());
        // 创建一个二维数组 nodes，用于存储节点信息
        int[][] nodes = new int[size][];
        // 读取输入的 size 行节点信息，并将其转换为二维数组
        for (int i = 0; i < size; i++) {
            nodes[i] = parseOneLine(scanner.nextLine());
        }
        // 读取输入的坐标信息，并将其转换为一维数组
        int[] coordinates = parseOneLine(scanner.nextLine());
        // 调用 query 方法查询节点的值，并将结果输出到控制台
        String result = query(nodes, coordinates[0], coordinates[1]);
        System.out.println(result);



    }

    // 定义一个方法，用于将一行字符串转换为整数数组
    private static int[] parseOneLine(String text) {
        // 使用正则表达式将字符串按空格分割为字符串数组
        String[] tokens = text.split("\\s+");
        // 创建一个整数数组 res，用于存储转换后的结果
        int[] res = new int[tokens.length];
        // 遍历 tokens 数组，将每个字符串转换为整数，并存储到 res 数组中
        for (int i = 0; i < tokens.length; i++) {
            res[i] = Integer.parseInt(tokens[i]);
        }
        // 返回转换后的整数数组
        return res;
    }

    private static String query(int[][] nodes, int x, int y) {
        // 如果 x 或 y 小于 0，则返回空集合
        if (x < 0 || y < 0) {
            return "{}";
        }
        // 创建一个 List 集合 res，用于存储所求层的所有数据
        List<Integer> res = new ArrayList<>();
        // 调用 dfs 方法，求出第 x 层所有数据并加入 res 集合中
        dfs(nodes, 0, x, res);
        // 如果 y 大于等于 res 集合的大小，则返回空集合
        if (y >= res.size()) {
            return "{}";
        }
        // 返回包含 res 集合中第 y 个元素的字符串
        return "{" + res.get(y) + "}";
    }

    private static void dfs(int[][] nodes, int idx, int x, List<Integer> res) {
        // 如果 x 等于 0，则将 nodes[idx][0] 加入 res 集合中，并返回
        if (x == 0) {
            res.add(nodes[idx][0]);
            return;
        }
        // 如果 nodes[idx] 只有一个元素，则返回
        if (nodes[idx].length == 1) {
            return;
        }
        // 遍历 nodes[idx] 中除第一个元素外的所有元素，递归调用 dfs 方法
        for (int i = 1; i < nodes[idx].length; i++) {
            dfs(nodes, nodes[idx][i], x - 1, res);
        }
    }

    @Override
    protected void mySolution() {

    }
}
