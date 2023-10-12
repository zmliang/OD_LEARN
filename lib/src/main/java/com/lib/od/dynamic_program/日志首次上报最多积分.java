package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

public class 日志首次上报最多积分 extends BaseTest {

    public int getResult(int[] logs){
        int len = logs.length;
        int[] plusScores = new int[len];
        plusScores[0] = logs[0];
        int[] minusScores = new int[len];
        minusScores[0] = logs[0];
        int[] results = new int[len];
        results[0] = logs[0];

        for (int i=0;i<len;i++){
            plusScores[i] = Math.min(100,plusScores[i-1]+logs[i]);
            minusScores[i] = minusScores[i-1]+plusScores[i-1];
            results[i] = plusScores[i]+minusScores[i];
            if (plusScores[i]>=100){
                break;
            }
        }

        int max = 0;
        for (int res:results){
            if (max<res){
                max =res;
            }
        }
        return max;

    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
