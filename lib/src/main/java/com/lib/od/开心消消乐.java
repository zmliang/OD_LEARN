package com.lib.od;


/**
 * 给定一个N行M列的二维矩阵，矩阵中每个位置的数字取值为0或1。矩阵示例如：
 * 1100
 * 0001
 * 0011
 * 1111
 * 现需要将矩阵中所有的1进行反转为0，
 * 规则如下：
 * 1） 当点击一个1时，该1便被反转为0，同时相邻的上、下、左、右，以及左上、
 *     左下、右上、右下8 个方向的1（如果存在1）均会自动反转为0；
 * 2）进一步地，一个位置上的1被反转为0时，与其相邻的8个方向的1（如果存在1）均会自动反转为0；
 *
 *<p>按照上述规则示例中的矩阵只最少需要点击2次后，所有值均为0。请问，给定一个矩阵，最少需要点击几次后，所有数字均为0？</p>
 */
public class 开心消消乐 extends BaseTest{

    /**
     * BFS+计数
     */
    @Override
    protected void officialSolution() {
        int rows = scanner.nextInt(); // 输入矩阵的行数
        int cols = scanner.nextInt(); // 输入矩阵的列数
        int[][] matrix = new int[rows][cols]; // 定义一个rows行cols列的矩阵
        for (int i = 0; i < rows; i++) { // 遍历矩阵的每一行
            for (int j = 0; j < cols; j++) { // 遍历矩阵的每一列
                matrix[i][j] = scanner.nextInt(); // 读入矩阵的每一个元素
            }
        }

        int result = 0; // 定义结果变量，表示矩阵中1的连通块数量
        for (int i = 0; i < rows; i++) { // 遍历矩阵的每一行
            for (int j = 0; j < cols; j++) { // 遍历矩阵的每一列
                // 从任意一个位置的1开始遍历
                if (matrix[i][j] == 1) { // 如果当前位置是1
                    result++; // 连通块数量加1
                    dfs(matrix, i, j); // 对以当前位置为起点的连通块进行深度优先遍历
                }
            }
        }
        System.out.println(result); // 输出矩阵中1的连通块数量

    }

    public static void dfs(int[][] matrix, int x, int y) {
        matrix[x][y] = 0; // 将当前位置的值设为0，表示已经遍历过
        int rows = matrix.length; // 矩阵的行数
        int cols = matrix[0].length; // 矩阵的列数
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // 定义8个方向的偏移量
        for (int[] dir : directions) { // 遍历8个方向
            int nextX = x + dir[0]; // 计算下一个位置的行坐标
            int nextY = y + dir[1]; // 计算下一个位置的列坐标
            if (nextX >= 0 && nextX < rows && nextY >= 0 && nextY < cols && matrix[nextX][nextY] == 1) { // 如果下一个位置在矩阵范围内且值为1
                dfs(matrix, nextX, nextY); // 对下一个位置进行深度优先遍历
            }
        }
    }

    @Override
    protected void mySolution() {
        int[][] matrix = new int[][]{};

        int ret = 0;
        for (int i=0; i<matrix.length;i++){
            for (int j=0; j<matrix[0].length;j++){
                if (_dfs(matrix,i,j)){
                    ret++;
                }
            }
        }
    }


    private int[][] directions = new int[][]{{-1,0},{1,0},{0,-1},{0,1},{1,1},{1,-1},{-1,-1},{-1,1}};


    public boolean _dfs(int[][] matrix,int x,int y){
        for (int[] direction:directions){
            int _x = direction[0];
            int _y = direction[1];


            _dfs(matrix,x+_x,y+_y);
        }
        return false;
    }
}
