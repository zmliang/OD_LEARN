package com.lib.od;

import java.util.HashSet;
import java.util.Set;

/**
 * 题目描述
 * Linux操作系统有多个发行版，distrowatch.com提供了各个发行版的资料。这些发行版互相存在关联，例如Ubuntu基于Debian开发，
 * 而Mint又基于Ubuntu开发，那么我们认为Mint同DebianQ也存在关联。
 * 发行版集是一个或多个相关存在关联的操作系统发行版，集合内不包含没有关联的发行版
 * 给你一个 n*n 的矩阵isConnected，其中 isConnectedlijl]= 1 表示第i个发行版和第j个发行版直接关联，而 isConnected[illi]= 0
 * 表示二者不直接相连。
 * 返回最大的发行版集中发行版的数量
 * 输入描述
 * 第一行输入发行版的总数量N,
 * 之后每行表示各发行版间是否直接相关
 * 输出描述
 * 输出最大的发行版集中发行版的数量
 *
 *
 *
 * 题目解析
 * 本题可以利用并查集求解，本题要求的就是各个连通分量的节点数，并输出最大的连通分量的节点数。
 *
 */
public class 字符串重新排序 extends BaseTest{

    public static void check(Set<Integer> currentVersionSet,int n,int[][] matrix){
        for (int i=0;i<matrix.length;i++){
            if (currentVersionSet.contains(i)){//如果该节点被访问过，跳过
                continue;
            }
            if (n!=i && matrix[n][i] == 1){//如果当前版本与n版本相连，则将其加入到版本集合中
                currentVersionSet.add(i);
                check(currentVersionSet,i,matrix);//继续递归以i节点为起点的关联版本
            }
        }
    }


    @Override
    protected void officialSolution() {
        int numOfVersions = scanner.nextInt();
        int[][] matrix = new int[numOfVersions][numOfVersions];
        for (int i=0;i<numOfVersions;i++){
            for (int j=0;j<numOfVersions;j++){
                matrix[i][j] = scanner.nextInt();
            }
        }

        //记录已经遍历过的版本号
        Set<Integer> visitedVersions = new HashSet<>();
        int res = 0;//最大关联版本数量
        for (int i=0;i<numOfVersions;i++){
            if (!visitedVersions.contains(i)){//若当前版本已经遍历过，跳过
                Set<Integer> currentVersionSet = new HashSet<>();
                check(currentVersionSet,i,matrix);//DFS找到i版本为起点的关联版本
                res = Math.max(res,currentVersionSet.size());
                visitedVersions.addAll(currentVersionSet);//将当前版本集合中的所有版本加入到visited
            }
        }

        System.out.println(res);//输出最大关联版本数量

    }

    @Override
    protected void mySolution() {

    }
}



















