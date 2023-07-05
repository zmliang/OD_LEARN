package com.lib.od;

import java.util.Arrays;
import java.util.Collections;

public class 最大利润 extends BaseTest{

    public int maxProfit_1(int[] prices){
        int min =Integer.MIN_VALUE;// Arrays.stream(prices).min().getAsInt();
        int ret= Integer.MIN_VALUE;
        for (int i=0;i<prices.length;i++){
            if (min>prices[i]){
                min = prices[i];
            }else if (prices[i]-min>ret){
                ret = prices[i]-min;
            }
        }
        return ret;
    }

    public int maxProfit_2(int[] prices) {

        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 0：持有现金
        // 1：持有股票
        // 状态转移：0 → 1 → 0 → 1 → 0 → 1 → 0
        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 这两行调换顺序也是可以的
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[len - 1][0];
    }



    @Override
    protected void officialSolution() {
        int num = scanner.nextInt();//商品件数
        int days = scanner.nextInt();//天数

        int[] maxItems = new int[num];
        for (int i=0;i<num;i++){
            maxItems[i] = scanner.nextInt();
        }

        int[][] prices = new int[num][days];//行：
        for (int i=0;i<num;i++){
            for (int j=0;j<days;j++){
                prices[i][j] = scanner.nextInt();
            }
        }

        int maxProfit = 0;
        for (int i=0;i<prices.length;i++){
            int[] day = prices[i];
            int ans = 0;
            for (int j=1;j<day.length;j++){
                ans+=Math.max(0,prices[i][j]-prices[i][j-1]);
            }
            maxProfit += ans*maxItems[i];
        }

    }


    public void mySolution() {
        int number = 3;
        int day = 3;
        int[] item = new int[]{4,5,6};//每件商品最大持有数量
        int[][] item_prices = new int[][]{{1,2,3},{4,3,2},{1,5,2}};//第i件商品每天的价格

        int result = 0;
        for (int i=0;i<item_prices.length;i++){//第几件商品
            int[] price = item_prices[i];
            int count = 0;
            for (int j=1;j<price.length;j++){//第几天
                if (price[j]-price[j-1]>0){
                    count+=price[j]-price[j-1];
                }
            }
            result+=item[i]*count;
        }

        System.out.println(result);


    }
}
