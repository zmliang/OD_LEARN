package com.lib.od;


/**
 * 使用动态规划前缀和
 */
public class 探索地块建立 extends BaseTest{

    public int getResult(int[][] matrix,int c,int k){
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] preSum = new int[row+1][col+1];

        for (int i=0;i<row;i++){
            preSum[i][0] = 0;
        }
        for (int i=0;i<col;i++){
            preSum[0][i] = 0;
        }

        for (int i=1;i<row;i++){
            for (int j=1;j<col;j++){
                preSum[i][j] = preSum[i-1][j]+preSum[i][j-1]-preSum[i-1][j-1]+
            matrix[i-1][j-1];
            }
        }

        int ret=0;

        for (int i=c;i<row;i++){
            for (int j=c;j<col;j++){
               int count = preSum[i][j]-(preSum[i-1][j]+preSum[i][j-1])+preSum[i-1][j-1];
               if (count>=k){
                   ret++;
               }
            }
        }
        return ret;
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
