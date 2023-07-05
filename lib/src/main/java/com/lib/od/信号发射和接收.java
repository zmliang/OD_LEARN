package com.lib.od;

public class 信号发射和接收 extends BaseTest{//TODO

    private void getResult(int[][] heights){
        int row= heights.length;
        int col = heights[0].length;
        int[][] ret = new int[row][col];

        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                ret[i][j] =calcSouth(heights,i,j)+calcEast(heights,i,j);
            }
        }
    }


    public int calcSouth(int[][] heights,int i,int j){
        if (j == 0){
            return 0;
        }
        int count = 1;
        int maxHeight = heights[i][j-1];
        for (int k=j-2;k>=0;k--){
            if (maxHeight>heights[i][j]){
                break;
            }
            if (heights[i][k]>maxHeight){
                count++;
                maxHeight = heights[i][k];
            }
        }
        return count;
    }

    public int calcEast(int[][] heights,int i,int j){
        if (i == 0){
            return 0;
        }
        int count = 1;
        int maxHeight = heights[i-1][j];
        for (int k=i-2;k>=0;k--){
            if (maxHeight>heights[i][j]){
                break;
            }
            if (heights[k][j]>maxHeight){
                count++;
                maxHeight = heights[k][j];
            }
        }
        return count;
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
