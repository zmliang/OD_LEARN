package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class 区间合并_leetcode extends BaseTest{
    @Override
    protected void officialSolution() {

        int[][] intervals = new int[][]{};

        Arrays.sort(intervals, (a, b) -> a[0]-b[0]);

        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);

        for (int i=01;i<intervals.length;i++){
            int[] item = intervals[i];
            int[] tmp = merged.get(merged.size()-1);
            if (tmp[1]<item[0]){
                merged.add(item);
            }else {
                tmp[1] = item[1];
            }
        }

    }

    @Override
    protected void mySolution() {

    }
}
