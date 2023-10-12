package com.lib.od;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 放暑假了，小明决定到某旅游景点游玩，他在网上搜索到了各种价位的酒店（长度为n的数组A），他的心理价位是x元，
 * 请帮他筛选出k个最接近x元的酒店（n&gt;=k&gt;0）,并打印酒店的价格。
 */
public class book_hotel extends BaseTest{


    //**

    /**
     * 1.酒店价格数组A和小明的心理价位x均为整型数据;(0&lt;n,k,x&lt;10000)
     * 2.优先选择价格最接近心理价位的酒店，若两家酒店和心理价位差价相同，则选择价格较低的酒店。(比如100元和300元距离心理价位200元同样接近，此时选择100元)
     * 3.酒店价格可能相同重复
     */
    @Override
    protected void officialSolution() {
        int numberOfWineshops = scanner.nextInt();
        int numberOfPickedWineshops = scanner.nextInt();
        int referencePrice = scanner.nextInt();

        int[] WineshopPrices = new int[numberOfWineshops];
        for (int i = 0; i < numberOfWineshops; i++) {
            WineshopPrices[i] = scanner.nextInt();
        }
        Arrays.sort(WineshopPrices);

        int[][] priceDifference = new int[numberOfWineshops][2];
        for (int i = 0; i < numberOfWineshops; i++) {
            int price = WineshopPrices[i];
            priceDifference[i][0] = price;
            priceDifference[i][1] = Math.abs(price - referencePrice);
        }

        List<int[]> sortedPriceDifference = Arrays.stream(priceDifference)
                .sorted(Comparator.comparingInt(Wineshop -> Wineshop[1]))
                .collect(Collectors.toList());

        List<Integer> pickedWineshopPrices = new ArrayList<>();
        for (int i = 0; i < numberOfPickedWineshops; i++) {
            pickedWineshopPrices.add(sortedPriceDifference.get(i)[0]);
        }

        pickedWineshopPrices.sort(Integer::compareTo);

        for (int i = 0; i < pickedWineshopPrices.size(); i++) {
            System.out.print(pickedWineshopPrices.get(i));
            if (i != pickedWineshopPrices.size() - 1) {
                System.out.print(" ");
            }
        }

    }

    @Override
    protected void mySolution() {
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int x = scanner.nextInt();
        int[] hotels = new int[n];
        for (int i=0;i<n;i++){
            hotels[i] = scanner.nextInt();
        }

        List<Integer> ret = new ArrayList<>();




    }
}
