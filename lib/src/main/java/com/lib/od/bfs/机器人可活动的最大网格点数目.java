package com.lib.od.bfs;

import com.lib.od.BaseTest;

public class 机器人可活动的最大网格点数目 extends BaseTest {


    @Override
    protected void officialSolution() {
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] matrix = new int[m][n];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                matrix[i][j] = scanner.nextInt();
            }
        }

        int maxRange = 0;
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                boolean[][] visited = new boolean[m][n];
                int range = dfs(matrix,visited,i,j);
                maxRange = Math.max(maxRange,range);
            }
        }

    }

    private int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

    public int dfs(int[][] matrix,boolean[][] visited,int x,int y){
        visited[x][y] = true;
        int range = 1;
        for (int[] direction:directions){
            int new_x = x+direction[0];
            int new_y = y+direction[1];
            if (new_y>=0 && new_y<matrix[0].length &&
            !visited[new_x][new_y] &&
            Math.abs(matrix[x][y]-matrix[new_x][new_y])<=1){
                range = dfs(matrix, visited, new_x, new_y);
            }
        }
        return range;
    }

    @Override
    protected void mySolution() {

    }
}
