package com.lib.od;


import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>高铁城市圈对人们的出行、经济的拉动效果明显。每年都会规划新的高铁城市圈建设。</p>
 * <p>在给定：城市数量，可建设高铁的两城市间的修建成本列表、以及结合城市商业价值会固定建设的两城市建高铁。</p>
 * <p>请你设计算法，达到修建城市高铁的最低成本。</p>
 * <p>注意，需要满足城市圈内城市间两两互联可达(通过其他城市中转可达也属于满足条件）。</p>
 *
 * 第一行，包含此城市圈中城市的数量、可建设高铁的两城市间修建成本列表数量、必建高铁的城市列表。三个数字用空格间隔。
 * 可建设高铁的两城市间的修建成本列表，为多行输入数据，格式为3个数字，用空格分隔，长度不超过1000。
 * 固定要修建的高铁城市列表，是上面参数2的子集，可能为多行，每行输入为2个数字，以空格分隔。</li></ul>
 *
 * 最小生成树
 * 
 */
public class 最优高铁城市修建方案 extends BaseTest{//TODO

    @Override
    protected void officialSolution() {
        int cityCount = scanner.nextInt(); // 城市数量
        int pairCount1 = scanner.nextInt(); // 可建设高铁的两城市间修建成本列表数量
        int pairCount2 = scanner.nextInt(); // 必建高铁的城市列表数量

        // 可建设高铁的两城市
        int[][] cityPairs1 = new int[pairCount1][3]; // 创建二维数组
        for (int i = 0; i < pairCount1; i++) {
            cityPairs1[i][0] = scanner.nextInt(); // 输入城市1
            cityPairs1[i][1] = scanner.nextInt(); // 输入城市2
            cityPairs1[i][2] = scanner.nextInt(); // 输入修建成本
        }

        // 必建高铁的两城市
        int[][] cityPairs2 = new int[pairCount2][2]; // 创建二维数组
        for (int i = 0; i < pairCount2; i++) {
            cityPairs2[i][0] = scanner.nextInt(); // 输入城市1
            cityPairs2[i][1] = scanner.nextInt(); // 输入城市2
        }

        UF uf = new UF(cityCount); // 创建并查集对象

        // key为修建高铁的两个城市，value为费用
        HashMap<String, Integer> cityMap = new HashMap<>(); // 创建HashMap对象
        for (int[] cityPair : cityPairs1) {
            int city1 = cityPair[0], city2 = cityPair[1];
            cityMap.put(city1 < city2 ? city1 + "-" + city2 : city2 + "-" + city1, cityPair[2]); // 存储城市对应的修建成本
        }

        int result = 0; // 初始化result为0
        // 先计算必建高铁情况下的费用
        for (int[] cityPair : cityPairs2) {
            int city1 = cityPair[0], city2 = cityPair[1];
            result += cityMap.get(city1 < city2 ? city1 + "-" + city2 : city2 + "-" + city1); // 计算修建成本
            // 纳入并查集
            uf.union(city1, city2); // 合并城市
        }

        // 已经满足所有城市通车，直接返回
        if (uf.count == 1) { // 如果只有一个连通分量
            System.out.println(result); // 输出result
            return; // 结束程序
        }

        // 按修建费用排序
        Arrays.sort(cityPairs1, (a, b) -> a[2] - b[2]); // 对城市对应的修建成本进行排序

        for (int[] cityPair : cityPairs1) {
            int city1 = cityPair[0], city2 = cityPair[1];
            // 判断两城市是否相连
            if (uf.item[city1] != uf.item[city2]) {
                uf.union(city1, city2); // 合并城市
                // 若可以合入，则将对应的建造成本计入result
                result += cityPair[2]; // 计算修建成本
            }
            if (uf.count == 1) { // 如果只有一个连通分量
                break; // 结束循环
            }
        }

        // count > 1代表有的城市无法通车
        if (uf.count > 1) { // 如果有多个连通分量
            System.out.println(-1); // 输出-1
            return; // 结束程序
        }

        System.out.println(result); // 输出result
    }

    @Override
    protected void mySolution() {

    }
    

    // 并查集
    class UF {
        int[] item; // 存储并查集的数组
        int count; // 连通分量的数量

        public UF(int n) { // 构造函数
            item = new int[n + 1]; // 初始化数组
            count = n; // 初始化连通分量数量
            for (int i = 0; i < n; i++)
                item[i] = i; // 初始化每个城市所在的连通分量
        }

        public int find(int x) { // 查找x所在的连通分量
            if (x != item[x]) { // 如果x不是根节点
                return (item[x] = find(item[x])); // 路径压缩，将x的父节点设置为根节点
            }
            return x; // 如果x是根节点，直接返回
        }

        public void union(int x, int y) { // 合并x和y所在的连通分量
            int xItem = find(x); // 查找x所在的连通分量
            int yItem = find(y); // 查找y所在的连通分量

            if (xItem != yItem) { // 如果x和y不在同一个连通分量中
                item[yItem] = xItem; // 将y所在的连通分量的根节点设置为x所在的连通分量的根节点
                count--; // 连通分量数量减1
            }
        }
    }

}
