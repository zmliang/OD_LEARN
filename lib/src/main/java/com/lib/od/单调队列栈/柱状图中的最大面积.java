package com.lib.od.单调队列栈;

import java.util.Stack;

public class 柱状图中的最大面积 {

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        int maxValue = 0;

        for (int i=0;i<len;i++){
            int h = heights[i];
            for (int j=i;j<len;j++){
                int w = Math.abs(j-i)+1;
                h = Math.min(h,heights[j]);
                maxValue = Math.max(maxValue,h*w);
            }
        }
        return maxValue;
    }

}
