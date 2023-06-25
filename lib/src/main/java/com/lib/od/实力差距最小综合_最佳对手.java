package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 题目描述
 * 游戏里面，队伍通过匹配实力相近的对手进行对战。但是如果匹配的队伍实力相差太大，对于双方游戏体验都不会太好.
 * 给定n个队伍的实力值，对其进行两两实力匹配，两支队伍实例差距在允许的最大差距d内，则可以匹配。
 * 要求在匹配队伍最多的情况下匹配出的各组实力差距的总和最小。
 * 输入描述
 * 第一行，n，d。队伍个数n。允许的最大实力差距d。
 * 。2<=n <=50
 * 。0<=d<=100
 * 第二行，n个队伍的实力值空格分割。
 * 。0<=各队伍实力值<=100
 * 输出描述
 * 匹配后，各组对战的实力差值的总和。若没有队伍可以匹配，则输出-1。
 *
 *题目解析
 * 这是一道动态规划只的题目。首先需要将输入的数据进行处理，得到每个队伍的实力值，并按照从小到大的顺序进行排序。然后需要定义两个数组pairs和min sum，其中oairsl表示匹配前个队伍所能得到的最大的pair数《(即匹配的队伍对数)，min suml表示匹配前个队五所能得到的最小的实力差距和。
 * 接下来，需要进行动态规划求解最小实力差距和。对于第i个队伍，有以下三种情况:
 * 。如果第个队伍和第-1个队伍的实力值差距小于等于d，则它们可以匹配，此时pairsli=pairs[i-2]+1，min suml=min sum[i2]+numsi-1-numsf-2，即匹配前-2个队伍所能得到的最大pair数加上1，匹配前2个队伍所能得到的最小实力差距和加上第-1个队伍和第i个队伍的实力值差距。
 * 如果匹配前-1个队伍所能得到的最大pair数大于匹配前-2个队伍所能得到的最大pair数，则pairslil=pairs[i-11,min sumI=min sumi-1]，即匹配前i-1个队伍所能得到的最大pair数和最小实力差距和。
 * 如果匹配前:1个队伍所能得到的最大pair数等于匹配前-2个队伍所能得到的最大pair数，则需要比较匹配前:1个队伍所能得到的最小实力差距和和匹配前-2个队伍所能得到的最小实力差距和加上第-1个队伍和第个队伍的实力值差距的最小值，取其中的较小值作为min sumlil，并将pairsli]赋值为pairsli-11。
 * 最后，如果pairs[n]等于0，则说明没有队伍可以匹配，输出-1，否则输出min sum[n].
 * 时间复杂度: O(nlogn)
 *
 */

public class 实力差距最小综合_最佳对手 extends BaseTest{

    public void dp(int[] nums,int d){
        List<Integer> pairs = new ArrayList<>(Collections.nCopies(nums.length+1,0));
        List<Integer> min_sum = new ArrayList<>(Collections.nCopies(nums.length+1,0));
        for (int i=2;i<nums.length+1;i++){
            int tmp = 0;
            if (nums[i-1] - nums[i-2]<=d){
                tmp+=1;
            }
            if (pairs.get(i-2)+tmp>pairs.get(i-1)){
                pairs.set(i,pairs.get(i-2)+tmp);
                min_sum.set(i,min_sum.get(i-2)+nums[i-1]-nums[i-2]);
            }else if (pairs.get(i-2)+tmp<pairs.get(i-1)){
                pairs.set(i,pairs.get(i-1));
                min_sum.set(i,min_sum.get(i-1));
            }else {
                if (tmp == 1){
                    min_sum.set(i,Math.min(min_sum.get(i-1),min_sum.get(i-2)+nums[i-1]-nums[i-2]));
                }else {
                    min_sum.set(i,Math.min(min_sum.get(i-1),min_sum.get(i-2)));
                }
                pairs.set(i,pairs.get(i-1));
            }
        }
        if (pairs.get(nums.length) == 0){
            System.out.println("-1");
        }else {
            System.out.println(pairs.get(nums.length));
        }
    }


    /**
     * 零钱兑换
     */
    int ret = 0;
    int coinChange_1(int[] coins, int amount){
        if (amount == 0){
            return 0;
        }
        if (amount<0){
            return -1;
        }
        for (int coin:coins){
            if (amount-coin<0){
                continue;
            }
            int subRet= coinChange_1(coins,amount-coin);
            ret = Math.min(subRet+1,ret);//在子问题中选择最优解，然后+1；
        }
        return ret;
    }


    /**
     * 动态规划解法
     * @param coins
     * @param amount
     */
    void coinChange_2(int[] coins,int amount){
        int[] dp = new int[amount+1];//
        Arrays.fill(dp,amount+1);
        dp[0] = 0;

        for (int i=0;i<dp.length;i++){
            for (int coin:coins){
                if (i-coin<0){
                    continue;
                }
                dp[i] = Math.min(dp[i],dp[i-coin]+1);
            }
        }
    }



    @Override
    protected void officialSolution() {
        int[] coins;
        int amount;



    }

    @Override
    protected void mySolution() {

    }


}



























