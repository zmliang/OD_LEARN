package com.lib.od;

import java.util.LinkedList;
import java.util.Queue;

public class 计算网络信号强度 extends BaseTest{


    @Override
    protected void officialSolution() {

        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        // 创建一个大小为 rows*cols 的数组，存储矩阵中每个位置的信号强度
        int[] signalStrength = new int[rows * cols];
        // 创建一个队列，存储所有信号源的位置
        Queue<int[]> sourcePositions = new LinkedList<>();

        // 读入矩阵并找到信号源位置
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                signalStrength[i * cols + j] = scanner.nextInt();
                // 如果该位置的信号强度大于 0，说明这是一个信号源的位置，将其加入队列
                if (signalStrength[i * cols + j] > 0) {
                    sourcePositions.offer(new int[]{i, j});
                }
            }
        }

        // 广度优先搜索传播信号
        while (!sourcePositions.isEmpty()) {
            // 取出队列头部的位置
            int[] pos = sourcePositions.poll();
            int i = pos[0], j = pos[1];

            // 如果信号强度为1，则不需要再传播了，后面肯定都是0
            if (signalStrength[i * cols + j] == 1) {
                break;
            }

            // 信号可以上下左右传播，存储四个方向的偏移量
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            // 遍历四个方向
            for (int[] direction : directions) {
                // 计算新位置的坐标
                int newI = i + direction[0], newJ = j + direction[1];
                // 如果新位置在矩阵范围内，且该位置的信号强度为0，说明可以传播到该位置
                if (newI >= 0 && newI < rows && newJ >= 0 && newJ < cols && signalStrength[newI * cols + newJ] == 0) {
                    // 将该位置的信号强度设为当前位置的信号强度减1，并将其加入队列
                    signalStrength[newI * cols + newJ] = signalStrength[i * cols + j] - 1;
                    sourcePositions.offer(new int[]{newI, newJ});
                }
            }
        }

        // 输出目标位置的信号强度
        int targetRow = scanner.nextInt(), targetCol = scanner.nextInt();
        System.out.println(signalStrength[targetRow * cols + targetCol]);
    }

    @Override
    protected void mySolution() {

    }
}
