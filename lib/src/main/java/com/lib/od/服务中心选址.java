package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 服务中心选址 extends BaseTest{//TODO
    // 计算当前位置到所有中心点的距离
    public static double calculateDistance(double pos, List<List<Double>> centers) {
        double dis = 0;
        for (List<Double> center : centers) {
            // 如果当前位置在中心点左侧，则加上中心点与当前位置的距离
            if (center.get(1) < pos)
                dis += pos - center.get(1);
                // 如果当前位置在中心点右侧，则加上当前位置与中心点的距离
            else if (pos < center.get(0))
                dis += center.get(0) - pos;
        }
        return dis;
    }

    // 更新最小和最大位置
    public static void updateMinMaxPos(double[] min_max_pos, List<List<Double>> centers) {
        double min_pos = Double.MAX_VALUE;
        double max_pos = Double.MIN_VALUE;
        for (List<Double> center : centers) {
            double left = center.get(0);
            double right = center.get(1);
            min_pos = Math.min(min_pos, Math.min(left, right));
            max_pos = Math.max(max_pos, Math.max(left, right));
        }
        min_max_pos[0] = min_pos;
        min_max_pos[1] = max_pos;
    }

    // 判断当前位置是否为最小距离的位置
    public static boolean isMinDistance(double distance, double distance_left, double distance_right) {
        return distance <= distance_left && distance <= distance_right;
    }

    // 更新最小位置和最大位置
    public static void updateMinMaxPos(double[] min_max_pos, double mid_pos, double distance, double distance_left, double distance_right) {
        if (distance < distance_left) {
            min_max_pos[0] = mid_pos + 0.5;
        } else if (distance < distance_right) {
            min_max_pos[1] = mid_pos - 0.5;
        }
    }



    @Override
    protected void officialSolution() {
        // 处理输入
        int n = scanner.nextInt();

        double[] min_max_pos = new double[2];
        min_max_pos[0] = 0;
        min_max_pos[1] = Double.MAX_VALUE;
        List<List<Double>> centers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double left = scanner.nextDouble();
            double right = scanner.nextDouble();
            centers.add(Arrays.asList(left, right));
        }
        updateMinMaxPos(min_max_pos, centers);

        // 二分查找最小距离
        while (min_max_pos[0] < min_max_pos[1]) {
            // 取中间位置
            double mid_pos = Math.ceil((min_max_pos[0] + min_max_pos[1]) / 2);

            // 计算当前位置到所有中心点的距离，以及左右相邻位置到所有中心点的距离
            double distance = calculateDistance(mid_pos, centers);
            double distance_left = calculateDistance(mid_pos - 0.5, centers);
            double distance_right = calculateDistance(mid_pos + 0.5, centers);

            // 如果当前位置的距离最小，则输出距离并结束程序
            if (isMinDistance(distance, distance_left, distance_right)) {
                System.out.println((int) distance);
                return;
            }

            // 如果左侧相邻位置的距离更小，则将最小位置更新为左侧相邻位置
            // 如果右侧相邻位置的距离更小，则将最大位置更新为右侧相邻位置
            updateMinMaxPos(min_max_pos, mid_pos, distance, distance_left, distance_right);
        }

        System.out.println(0);

    }

    @Override
    protected void mySolution() {

    }
}
