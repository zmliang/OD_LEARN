package com.lib.od;

public class 上班之路是否能到达公司 extends BaseTest{

    int[][] directions = new int[][]{{1,0,1},{-1,0,2},{0,1,3},{0,-1,4}};
    boolean[][] visited = new boolean[][]{};
    String[][] matrix = new String[][]{};
    public boolean dfs(int x,int y,boolean[][] visit,int useTurn,int useBreaks,int lastDirect){
        if (matrix[x][y].equals("T")){
            return true;
        }
        for (int[] item:directions){
            int direct = item[3];
            int new_x = x+item[0];
            int new_y = y+item[1];
            if (new_y>=0 && !visit[new_x][new_y]){
                if (lastDirect!=direct){
                    if (useTurn>0){

                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
