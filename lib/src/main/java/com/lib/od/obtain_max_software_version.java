package com.lib.od;


import java.util.Scanner;

/**
 * 获取最大软件版本号
 */
public class obtain_max_software_version extends BaseTest{



    @Override
    void officialSolution() {
        // 输入版本号
        String version1 = scanner.nextLine(); // 获取第一个版本号
        String version2 = scanner.nextLine(); // 获取第二个版本号

        // 按照“.”分割版本号
        String[] version1Arr = version1.split("\\."); // 将第一个版本号按照“.”分割成字符串数组
        String[] version2Arr = version2.split("\\."); // 将第二个版本号按照“.”分割成字符串数组

        // 比较前两位版本号
        for (int i = 0; i < 2; i++) { // 循环前两位版本号
            int v1 = Integer.parseInt(version1Arr[i]); // 将第一个版本号的当前位转换为整数
            int v2 = Integer.parseInt(version2Arr[i]); // 将第二个版本号的当前位转换为整数
            if (v1 != v2) { // 如果两个版本号当前位不相等
                System.out.println(v1 > v2 ? version1 : version2); // 输出大的版本号
                return; // 结束程序
            }
        }

        // 比较第三位版本号
        if (version1Arr.length > 2 && version2Arr.length > 2) { // 如果第一个版本号和第二个版本号都有第三位版本号
            String[] version1ThirdArr = version1Arr[2].split("-"); // 将第一个版本号的第三位按照“-”分割成字符串数组
            String[] version2ThirdArr = version2Arr[2].split("-"); // 将第二个版本号的第三位按照“-”分割成字符串数组
            int v1 = Integer.parseInt(version1ThirdArr[0]); // 将第一个版本号的第三位转换为整数
            int v2 = Integer.parseInt(version2ThirdArr[0]); // 将第二个版本号的第三位转换为整数
            if (v1 != v2) { // 如果两个版本号的第三位不相等
                System.out.println(v1 > v2 ? version1 : version2); // 输出大的版本号
                return; // 结束程序
            }
            if (version1ThirdArr.length == 2 && version2ThirdArr.length == 2) { // 如果第一个版本号和第二个版本号的第三位都包含“-”
                System.out.println(version1ThirdArr[1].compareTo(version2ThirdArr[1]) >= 0 ? version1 : version2); // 比较两个版本号的第三位后面的字符串，输出大的版本号
            } else { // 如果第一个版本号和第二个版本号的第三位不都包含“-”
                System.out.println(version1ThirdArr.length >= version2ThirdArr.length ? version1 : version2); // 比较两个版本号的第三位的长度，输出长的版本号
            }
        } else { // 如果第一个版本号和第二个版本号都没有第三位版本号
            System.out.println(version1Arr.length >= version2Arr.length ? version1 : version2); // 比较两个版本号的长度，输出长的版本号
        }
    }

    @Override
    protected void mySolution() {

    }
}
