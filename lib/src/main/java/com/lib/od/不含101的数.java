package com.lib.od;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数位DP
 *
 * <p>小明在学习二进制时，发现了一类不含 101的数，也就是：
 * 将数字用二进制表示，不能出现 101 。
 *  现在给定一个整数区间 [l,r] ，请问这个区间包含了多少个不含 101 的数？
 *
 *
 *  ，本题乍看是很简单的题目，直接暴力法不就得了。但是你注意看范围是【1 ≤ l ≤ r ≤ 10^9】，暴力肯定会超时。这题使用的是数位DP
 *
 *
 *  这道题可以使用数位DP来解决。具体思路是从高位到低位逐位枚举，对于每一位，
 *  枚举它的取值，并根据前一位和前两位的值来判断是否符合条件。同时，使用记忆化数组来避免重复计算。
 *  具体实现中，可以将数字转换为二进制数，然后递归处理每一位。
 *  递归函数中，p表示当前处理到的二进制位，limit表示当前位是否受到上限制，f表示记忆化数组，arr表示二进制数，
 *  pre表示前一位的值，prepre表示前两位的值。递归结束条件是处理完所有二进制位，此时返回1。在递归过程中，
 *  统计符合条件的数的个数，并使用记忆化数组避免重复计算。</p>
 *
 *  最终，可以通过调用countBinaryWithout101函数来统计区间[L, R]内不含101的数的个数，具体实现中，
 *  使用countBinaryWithout101® - countBinaryWithout101(L - 1)来统计。
 */
public class 不含101的数 extends BaseTest{//TODO
    // 递归函数，p表示当前处理到的二进制位，limit表示当前位是否受到上限制，f表示记忆化数组，arr表示二进制数，pre表示前一位的值，prepre表示前两位的值
    public static int dfs(int p, boolean limit, int[][][] f, List<Integer> binary, int pre, int prepre) {
        // 当处理完所有二进制位时，返回1
        if (p == binary.size()) return 1;

        // 如果当前位没有受到上限制，则可以使用记忆化数组
        if (!limit && f[p][pre][prepre] != 0) return f[p][pre][prepre];

        // 当前位的最大值
        int max_val = limit ? binary.get(p) : 1;
        int count = 0;

        // 枚举当前位的值
        for (int i = 0; i <= max_val; i++) {
            // 如果当前位为1，且前一位和前两位都为0，则不符合条件
            if (i == 1 && pre == 0 && prepre == 1) continue;
            // 统计符合条件的数的个数
            count += dfs(p + 1, limit && i == max_val, f, binary, i, pre);
        }

        // 如果当前位没有受到上限制，则可以使用记忆化数组
        if (!limit) f[p][pre][prepre] = count;

        return count;
    }

    public static int countBinaryWithout101(int num) {
        // 将数字转换为二进制数
        List<Integer> binary = new ArrayList<>();
        while (num > 0) {
            binary.add(num % 2);
            num /= 2;
        }
        Collections.reverse(binary);

        // 初始化记忆化数组
        int[][][] f = new int[binary.size()][2][2];

        // 统计不含101的数的个数
        return dfs(0, true, f, binary, 0, 0);
    }

    @Override
    protected void officialSolution() {
        int L = 3;//scanner.nextInt();
        int R = 5;//scanner.nextInt();

        // 统计区间[L, R]内不含101的数的个数
        int count = countBinaryWithout101(R) - countBinaryWithout101(L - 1);
        System.out.println(count);
    }

    @Override
    protected void mySolution() {

    }
}
