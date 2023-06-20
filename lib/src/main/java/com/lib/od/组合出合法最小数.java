package com.lib.od;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 题目描述数组里面哦都是代表非负整数的字符串，将数组里所有的数值[排列组合Q]拼接起来组给一个数组
 * ，成一个数字，输出拼接成的最小的数字。
 * 输入描述
 * 个数组，数组不为空，数组里面都是代表非负整数的字符串，可以是0开头，例如:“13”“045”“09”“56”
 *
 * 数组的大小范围:[1,50]
 * 数组中每个元素的长度范围:[1,30]
 *
 *
 *
 * 题目解析
 * 经典的全排列算法。
 * 按照前面的规则将数组元素排序，排序后，检查数组头元素是否以“0”开头，如果是的话，则开始遍历数组后面的元素，
 * 直到找到一个不以“0”开头的元素x，然后将元素x取出，并插入到数组头部。如果-直找不到这样的x，则说明数组元素
 * 全部是以0开头的，此时直接拼接出 组合数Q，然后去除前导0.
 *
 */

public class 组合出合法最小数 extends BaseTest{

    public static List<String> splitStringToStringArray(String params_str,String op){
        List<String> str_array = new ArrayList<>();
        while (params_str.indexOf(op)!=-1){
            int found = params_str.indexOf(op);
            str_array.add(params_str.substring(0,found));
            params_str = params_str.substring(found+1);
        }
        str_array.add(params_str);
        return str_array;
    }

    public static boolean compareStrings(String str1,String str2){
        return Integer.parseInt(str1+str2) < Integer.parseInt(str2+str1);
    }


    @Override
    protected void officialSolution() {
        String input_str = scanner.nextLine();
        List<String> numbers = splitStringToStringArray(input_str," ");
        Collections.sort(numbers, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if (compareStrings(s,t1)){
                    return -1;
                }else {
                    return 1;
                }
            }
        });

        if (numbers.get(0).charAt(0) == '0'){
            for (int i=1;i<numbers.size();i++){
                if (numbers.get(i).charAt(0)!='0'){
                    numbers.set(0,numbers.get(i)+numbers.get(0));
                    numbers.set(i,"");
                    break;
                }
            }
        }


        String result = "";
        for (String str:numbers){
            result+=str;
        }

        System.out.println(result.replaceFirst("^0+(?!$)",""));

    }

    @Override
    protected void mySolution() {

    }
}
























