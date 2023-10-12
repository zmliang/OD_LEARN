package com.lib.od;
import java.util.*;
/**
 * 题目描述
 * 模拟商场优惠打折，有三种优惠券可以用，满减券、打折券和无门槛券满减券: 满100减10，
 * 满200减20，满300减30，满400减40，以此类推不限制使用:打折券:固定折扣92折，且打折
 * 之后向下取整，每次购物只能用1次;无门槛券:一张券减5元，没有使用限制。
 * 每个人结账使用 优惠券Q时有以下限制:
 * 每人每次只能用两种优惠券，并且同一种优惠券必须一次用完，不能跟别的穿插使用(比如用一张满减，再用一张打折，再用一张满减,这种顺序不行)
 * 求不同使用顺序下每个人用完券之后得到的最低价格和对应使用优惠券的总数;如果两种顺序得到的价格一样低，就取使用优惠券数量较少的那个。
 * 输入描述
 * 第一行三个数字m,n,k，分别表示每个人可以使用的满减券、打折券和无门槛券的数量:第二行一个数字x,表示有几个人购物:
 * 后面x行数字，依次表示是这几个人打折之前的商品总价。
 * 输出描述
 * 输出每个人使用券之后的最低价格和对应使用优惠券的数量
 *
 *题目解析
 * 本题的解题思路如下，首先实现满减，打折，无门槛的逻辑!
 * ·满减逻辑，只要总价price大于等于100，且还有满减券，则不停orice -= Math.foor(price / 100)*10; 直到总价price小于100，或者满减券用完。
 * ·打折逻辑，按照题目意思，打折券只能使用一次，因此无论打折券有多少张，都只能使用一次，因此只要打折券数量大于等于1，那么price = Math.floor(price *0.92);
 * 无门槛逻辑，只要总价price大于0，且还有无门槛券，则不停orice -= 5:直到price小于等于0，或者无门槛券用完
 * 接下来就是求上面三种逻辑的任选2个的排列:
 * 假设满减是M，打折是N，无门槛是K，则有排列如下:
 * 。MN、NM
 * 。MK、KM
 * 。NK、KN
 * 注意，券的使用对顺序敏感
 * 因此，求出以上排列后，对每个人的总价使用六种方式减价，只保留减价最多，用券最少的那个优化思路:
 * 对于无门槛券的使用，无门槛券总是在最后使用才会最优对于满减来说，无门槛肯定是最后使用最优惠，
 *对于92折来说
 * 先用无门槛后打折(x-5y)*0.92 =x*0.92 -5*0.92*y
 * 。先打折后用无门槛 x*0.92 - 5y
 * 对比可以看出，先92折，再无门槛最优惠，因此确实可以直接排除KM和KN的情况，即先无门槛的情况。
 * 代码思路
 * 这是一道模拟题，主要思路是对每个购物者依次计算不同优惠券使用顺序下的最低价格和对应使用优惠券的总数。具体实现如下:
 * 1.首先读入每个人可以使用的满减券、打折券和无门槛券的数量，以及有几个人购物和每个人的商品总价。
 * 2.对每个人的商品总价，分别计算满减券和打折券、满减券和无门槛券、打折券和满减券、打折券和无门槛券四种优惠券使用顺序下的最低价格和对应使用优惠券的总数。
 * 3.将四种优惠券使用顺序下的最低价格和对应使用优惠券的总数存储到一个 List 中，并按照价格和使用优惠券数量排序
 * 4.输出最低价格和对应使用优惠券的总数,
 * 5.重复以上步骤，直到处理完所有购物者的商品总价。
 * 注意，满减券和打折券、满减券和无门槛券、打折券和满减券、打折券和无门槛券四种优惠券使用顺序下的使用顺序必须一次用完，不能跟别的穿插使用。
 *因此，在计算满减券和打折券、满减券和无门槛券、打折券和满减券、打折券和无门槛差四种优惠差使用顺序下的最任价格和对应使用优惠券的总数时，
 * 需要分别计算每种优惠券使用后的商品总价，并在计算下一种优惠券使用时使用这个商品总价。
 *
 *
 */
public class 模拟商场优惠打折 extends BaseTest{
    // 满减券的使用情况
    public static int[] fullSubtraction(int price, int m) {
        while (price >= 100 && m > 0) {
            price -= (int) (price / 100) * 10;
            m -= 1;
        }
        return new int[]{price, m};
    }

    // 打折券的使用情况
    public static int[] discount(int price, int n) {
        if (n >= 1) {
            price = (int) (price * 0.92);
            n -= 1;
        }
        return new int[]{price, n};
    }

    // 无门槛券的使用情况
    public static int[] thresholdFree(int price, int k) {
        while (price > 0 && k > 0) {
            price -= 5;
            price = Math.max(price, 0);
            k -= 1;
        }
        return new int[]{price, k};
    }

    @Override
    protected void officialSolution() {
// 输入每个人可以使用的满减券、打折券和无门槛券的数量
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        // 输入有几个人购物
        int x = scanner.nextInt();
        // 用 List 存储每个人的商品总价
        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            prices.add(scanner.nextInt());
        }
        for (int price : prices) {
            // 用 List 存储每个人的不同使用顺序下的优惠券使用情况
            List<int[]> ans = new ArrayList<>();
            // 满减券和打折券的使用顺序
            int[] resM = fullSubtraction(price, m);
            int[] resMN_N = discount(resM[0], n);
            ans.add(new int[]{resMN_N[0], m + n - (resM[1] + resMN_N[1])});
            // 满减券和无门槛券的使用顺序
            int[] resMK_K = thresholdFree(resM[0], k);
            ans.add(new int[]{resMK_K[0], m + k - (resM[1] + resMK_K[1])});
            // 打折券和满减券的使用顺序
            int[] resN = discount(price, n);
            int[] resNM_M = fullSubtraction(resN[0], m);
            ans.add(new int[]{resNM_M[0], n + m - (resN[1] + resNM_M[1])});
            // 打折券和无门槛券的使用顺序
            int[] resNK_K = thresholdFree(resN[0], k);
            ans.add(new int[]{resNK_K[0], n + k - (resN[1] + resNK_K[1])});
            // 按照价格和使用优惠券数量排序
            Collections.sort(ans, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] != o2[0]) {
                        return o1[0] - o2[0];
                    } else {
                        return o1[1] - o2[1];
                    }
                }
            });
            // 输出最低价格和对应使用优惠券的总数
            System.out.println(ans.get(0)[0] + " " + ans.get(0)[1]);
        }
    }

    @Override
    protected void mySolution() {

    }
}
