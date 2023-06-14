package com.lib.od;

/**
 * 题目描述
 * 快递业务范围有N个站点，A站点与B站点可以中转快递，则认为A-B站可达，如果A-B可达，B-C可达，则A-C可达,
 * 现在给N个站点编号0、1、...n-1，用s[I]表示Hj是否可达，sIIi=1表示j可达，slIIi=0表示jj不可达。
 * 现用二维数组给定N个站点的可达关系，请计算至少选择从几个主站点出发，才能可达所有站点(覆盖所有站点业务)
 * 说明: s[ijij与s[ijj取值相同。
 * 输入描述
 * 第一行输入为N (1<N < 10000)，N表示站点个数。
 * 之后N行表示站点之间的可达关系，第i行第i个数值表示编号为i和i之间是否可达。
 * 输出描述
 * 输出站点个数，表示至少需要多少个主站点。
 *
 *
 * 题目解析
 * 本题其实就是求解图中 连通分量Q个数
 * 本题使用 回溯法Q 或并查集
 */
public class 计算快递主站点 extends BaseTest{

    static class UnionFind {
        int[] parent; // 记录每个节点的父节点
        int count; // 记录连通分量的个数

        public UnionFind(int n) { // 构造函数，初始化parent数组和count
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // 初始时每个节点的父节点是它自己
            }
            count = n;
        }

        public int find(int x) { // 查找x所在的连通分量的代表
            if (x != parent[x]) { // 如果x不是它所在连通分量的代表
                parent[x] = find(parent[x]); // 将x的父节点更新为它所在连通分量的代表
            }
            return parent[x]; // 返回x所在连通分量的代表
        }

        public void unionNodes(int x, int y) { // 合并x和y所在的连通分量
            int rootX = find(x), rootY = find(y); // 找到x和y所在连通分量的代表
            if (rootX != rootY) { // 如果x和y不在同一个连通分量中
                parent[rootX] = rootY; // 将x所在连通分量的代表的父节点更新为y所在连通分量的代表
                count--; // 连通分量的个数减1
            }
        }
    }

    @Override
    void officialSolution() {
        int n = scanner.nextInt();
        int[][] sites = new int[n][n]; // 定义一个n*n的矩阵sites
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = scanner.nextInt(); // 输入矩阵sites
            }
        }

        UnionFind uf = new UnionFind(n); // 定义一个并查集，初始时有n个节点
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // 遍历上三角矩阵
                if (sites[i][j] == 1) { // 如果i和j可达，即sites[i][j]为1
                    uf.unionNodes(i, j); // 合并i和j所在的连通分量
                }
            }
        }

        System.out.println(uf.count); // 输出连通分量的个数

    }

    @Override
    protected void mySolution() {

    }
}
