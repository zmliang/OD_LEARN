package com.lib.od;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 最多获得的短信条数

 0-1背包问题

 题目描述
 某云短信厂商，为庆祝国庆，推出充值优惠活动现在给出客户预算，和优惠售价序列，求最多可获得的短信总条数
 输入描述
 第一行客户预算M，其中 0≤M< 10^6第二行给出售价表，P1,P2,...Pn,其中 1 <n < 100Pi为充值i元获得的短信条数。1≤Pi< 1000，1<n < 100
 输出描述
 最多获得的短信条数

 */
public class package_0_1 extends BaseTest{


    @Override
    void officialSolution() {
        int money = Integer.parseInt(scanner.nextLine());
        //转为数组
        List<Integer> topup_info = Arrays.stream(scanner.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int[] dp = new int[money+1];

        for (int i = 0; i <= topup_info.size(); i++) {
            for (int j = 0; j <= money; j++) {
                if (!(i == 0 || j == 0 || j < i)) {
                    dp[j] = Math.max(dp[j], dp[j - i] + topup_info.get(i - 1));
                }
            }
        }
        System.out.println(dp[money]);
    }

    @Override
    protected void mySolution() {

    }
}
