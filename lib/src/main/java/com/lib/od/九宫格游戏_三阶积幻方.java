package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;

public class 九宫格游戏_三阶积幻方 extends BaseTest{

    private static ArrayList<Integer[]> result = new ArrayList<>();


    public void permute(Integer[] nums,int start,int end){
        if (start == end){
            if (check(nums)){
                result.add(Arrays.copyOf(nums,9));
            }
            return;
        }
        for (int i = start;i<=end;i++){
            swap(nums,start,i);//交换第一个start=i；即交换1和他自己，对后面的数字进行递归
            permute(nums,start+1,end);
            swap(nums,start,i);
        }
    }

    private static void swap(Integer[] nums,int k,int i){
        int tmp = nums[k];
        nums[k] = nums[i];
        nums[i] = tmp;
    }


    private static boolean check(Integer[] nums){
        int fixedRet = nums[0]*nums[1]*nums[2];
        //每行
        if (fixedRet != nums[3]*nums[4]*nums[5] ||
                fixedRet != nums[6]*nums[7]*nums[8]){
            return false;
        }
        //每列
        if (fixedRet != nums[0]*nums[3]*nums[6] ||
                fixedRet != nums[1]*nums[4]*nums[7]||
                fixedRet != nums[2]*nums[5]*nums[8]){
            return false;
        }
        //对角线
        if (fixedRet!=nums[0]*nums[4]*nums[8] ||
                fixedRet!=nums[2]*nums[4]*nums[6]){
            return false;
        }

        return true;
    }


    @Override
    protected void officialSolution() {

    }



    @Override
    protected void mySolution() {

    }
}
