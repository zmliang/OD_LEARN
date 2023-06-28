package com.lib.od;

public class 螺旋矩阵 extends BaseTest{
    @Override
    protected void officialSolution() {
        int n = 3;
        int[][] matrix = new int[3][3];
        int startX = 0;
        int startY = 0;
        int end = n;
        int value = 0;
        int circleCount = n/2;
        while (circleCount>0){
            int x = startX;
            int y = startY;

            for (int i=x;i<end;i++){//top
                matrix[x][y] = value++;
            }

            startX++;
            startY++;
            circleCount--;
        }
    }

    @Override
    protected void mySolution() {

    }
}
