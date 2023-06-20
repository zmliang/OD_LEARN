package com.lib.od;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 题目描述
 * 有N条线段，长度分别为a[1]-a[n]。
 * 现要求你计算这N条线段最多可以组合成几个直角三角形。
 * 每条线段只能使用一次，每个三角形包含三条线段
 * 输入描述
 * 第一行输入一个正整数T (1<=T<= 100) ，表示有T组测试数据
 * 对于每组测试数据，接下来有T行.
 * 每行第一个正整数N，表示线段个数(3<=N<=20) ，接着是N个正整数，
 * 表示每条线段长度(0<a[lij<100)。
 * 输出描述
 * 对于每组测试数据输出一行，每行包括一个整数，表示最多能组合的直角三角形个数
 *
 *
 *
 *
 *
 */
public class 最多几个直角三角形 extends BaseTest{

    //存储所有符合条件的三角形周长
    public static ArrayList<Integer> trianglePerimeterList = new ArrayList<>();
    //存储所有符合条件的三角形
    public static ArrayList<Integer[]> triangleList = new ArrayList<>();

    /**
     *
     * @param nums 未使用的数字列表
     * @param index
     * @param sides 已经选中的三角形的三条边
     */
    public static void dfs(int[] nums,int index,LinkedList<Integer> sides){
        if (sides.size() == 3){
            if (sides.get(0)*sides.get(0)+sides.get(1)*sides.get(1) == sides.get(2)*sides.get(2)){
                triangleList.add(sides.toArray(new Integer[3]));
                return;
            }
        }
        for (int i = index;i<nums.length;i++){
            if (!(i>0&&nums[i] == nums[i-1])){
                sides.add(nums[i] );
                dfs(nums,i+1,sides);
                sides.removeLast();
            }
        }

    }


    /**
     * 求出所有符合条件的三角形的周长
     * @param index
     * @param count
     * @param num
     */
    public static void check(int index,int[] count,int num){
        if (index>=triangleList.size()){
            trianglePerimeterList.add(num);
            return;
        }
        for (int i=index;i<triangleList.size();i++){
            Integer[] singleTriangle = triangleList.get(i);
            int a = singleTriangle[0];
            int b = singleTriangle[1];
            int c = singleTriangle[2];
            if (count[a]>0 && count[b]>0 && count[c]>0){
                count[a]--;
                count[b]--;
                count[c]--;
                num++;
                check(i+1,count,num);
                num--;
                count[a]++;
                count[b]++;
                count[c]++;
            }
        }
        trianglePerimeterList.add(num);
    }

    @Override
    protected void officialSolution() {
        int testCases = scanner.nextInt();
        for (int i=0;i<testCases;i++){
            int n = scanner.nextInt();
            int[] nums = new int[n];
            for (int j=0;j<n;j++){
                nums[j] = scanner.nextInt();
            }

            Arrays.sort(nums);//对数组排序
            //求出所有符合条件的三角形
            dfs(nums,0,new LinkedList<>());
            //统计每个数字出现的次数
            int[] count = new int[100];
            for (int num:nums){
                count[num]++;
            }
            check(0,count,0);

            //找出最大的符合条件的三角形的周长
            int maxTrianglePerimeter = 0;
            for (int trianglePerimeter:trianglePerimeterList){
                if (trianglePerimeter>maxTrianglePerimeter){
                    maxTrianglePerimeter = trianglePerimeter;
                }
            }
            System.out.println(maxTrianglePerimeter);

            //重置
            trianglePerimeterList = new ArrayList<>();
        }
    }

    @Override
    protected void mySolution() {

    }
}
