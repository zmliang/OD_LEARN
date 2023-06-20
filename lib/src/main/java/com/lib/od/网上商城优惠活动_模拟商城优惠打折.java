package com.lib.od;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 *
 *
 * 主要分四种情况:
 * 。先满减，再打折
 * 。先打折，再满减
 * 。先满减，再无门槛
 * 。先打折，再无门槛
 * 注:因为无门槛是直接减，所以不能在满减和打折前面,还需考虑到优惠券是0的情况，因为满减和无门槛都是与张数有关,所有我们只考虑了有无打折券的情况
 *
 *
 *
 * 在重载或者Arrays.sort(a2,(a, b)->(b[0]-a[0]));中
 *
 * 我们观察(a,b)，若a-b或者比较器里面return a-b,则是升序排序
 *
 * 若b-a或者比较器里面return b-a,则是降序排序。
 *
 */
public class 网上商城优惠活动_模拟商城优惠打折 extends BaseTest{

    /**
     * 先满减，后打折的方式
     */
    public static int[] getModeA(int price,int m,int n){
        int count = 0;
        count+=Math.min(m,price/100);
        price-=count*10;
        price*=0.92;
        count+=1;
        return new int[]{price,count};
    }

    /**
     * 先打折，后满减的方式
     */
    public static int[] getModeB(int price,int m,int n){
        int count = 0;
        price*=0.92;
        count+=1;
        count+=Math.min(m,price/100);
        price-=(count-1)*10;
        return new int[]{price,count};
    }



    /**
     * 先满减，后无门槛
     */
    public static int[] getModeC(int price,int m,int k){
        int count = 0;
        count+=Math.min(m,price/100);
        price-=count*10;
        for (int i=0;i<k;i++){
            price-=5;
            count+=1;
            if (price<0){
                break;
            }
        }
        return new int[]{price,count};
    }

    /**
     * 先打折，后无门槛
     */
    public static int[] getModeD(int price,int n,int k){
        int count = 0;
        price*=0.92;
        count+=1;
        for (int i=0;i<k;i++){
            price-=5;
            count+=1;
            if (price<0){
                break;
            }
        }
        return new int[]{price,count};
    }

    @Override
    protected void officialSolution() {
        List<Integer> params = Arrays.stream(scanner.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int m = params.get(0);//满减
        int n = params.get(1);//打折
        int k = params.get(2);//无门槛

        int x = Integer.parseInt(scanner.nextLine());

        while (scanner.hasNext()){
            int price = Integer.parseInt(scanner.nextLine());
            int[][] result = new int[4][2];
            //分别调用4种计算方式，得到方案及其需要的优惠券数量
            result[0] = getModeA(price,m,n);
            result[1] = getModeB(price,m,n);
            result[2] = getModeC(price,m,k);
            result[3] = getModeD(price,n,k);

            //按照价格降序排列
            Arrays.sort(result,(int[] a,int[] b)->{
                if (a[0]!=b[0]){
                    return a[0]-b[0];
                }else {
                    return a[1]-b[1];
                }
            });

            System.out.println(result[0][0]);
            System.out.println(" ");
            System.out.println(result[0][1]);
        }
    }

    @Override
    protected void mySolution() {

    }
}




















