package com.lib.od;


/**
 * 二进制差异数
 *
 * 题目描述
 * 对于任意两个正整数A和B，定义它们之间的差异值和相似值:**差异值:**A、B转换成二进制后，对于二进制的每一位，
 * 对应位置的bit值不相同则为1，否则为0**相似值:*A、B转换成二进制后，对于二进制的每一位，对应位置的bit值都为1则为1，
 * 否则为0;现在有n个正整数AO到A (n-1) ，问有多少(i,j)(0<=i<j<n)，Ai和Aj的差异值大于相似值。假设A=5，B=3;则A的
 * 二进制表示101;B的二进制表示011;则A与B的差异值二进制为110;相似值二进制为001;A与B的差异值十进制等于6，相似值十进制等于1，满足条件
 * 输入描述
 * 个n接下来n个正整数
 *
 *
 *
 * https://blog.csdn.net/banxia_frontend/article/details/124620080
 *
 */
public class binary_difference_num extends BaseTest{

    @Override
    protected void officialSolution() {
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // 统计每个数二进制下最高位的位置
        int[] high_bit = new int[100];
        for (int i = 0; i < n; i++) {
            int a = arr[i];
            String bin = Integer.toBinaryString(a);
            int len = bin.length();
            if (bin.equals("0")) {
                high_bit[0]++;
            } else {
                high_bit[len - bin.indexOf('1') - 1]++;
            }
        }

        // 计算差异值大于相似值的对数
        int ans = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                if ((i ^ j) > (i & j)) {
                    ans += high_bit[i] * high_bit[j];
                }
            }
        }
        System.out.println(ans);
    }

    /**
     *
     */
    @Override
    protected void mySolution() {

    }
}
