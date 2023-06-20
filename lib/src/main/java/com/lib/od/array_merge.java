package com.lib.od;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class array_merge extends BaseTest{


    @Override
    protected void officialSolution() {
        // 处理输入
        int k = scanner.nextInt(); // 读取第一个整数，表示每个数组要取出的元素个数
        int n = scanner.nextInt(); // 读取第二个整数，表示数组的个数
        scanner.nextLine(); // 读取完整行

        // 初始化数组
        List<LinkedList<Integer>> numArrays = new ArrayList<>(); // 创建LinkedList<Integer>类型的ArrayList
        for (int i = 0; i < n; i++) { // 循环读取n个数组
            LinkedList<Integer> numArray = new LinkedList<>(); // 创建一个LinkedList<Integer>对象
            String[] nums = scanner.nextLine().split(","); // 读取一行，将其中的数字以逗号为分隔符分割成字符串数组
            for (String num : nums) { // 循环读取字符串数组中的每个数字
                numArray.add(Integer.parseInt(num)); // 将数字转换成整数并添加到LinkedList中
            }
            numArrays.add(numArray); // 将LinkedList添加到ArrayList中
        }

        StringBuilder builder = new StringBuilder(); // 创建StringBuilder对象，用于拼接字符串
        int index = 0; // 初始化索引
        while (numArrays.size() > 0) { // 当List中还有元素时循环
            LinkedList<Integer> singleArray = numArrays.get(index); // 获取当前索引位置的LinkedList
            // 取出前k个
            for (int i = 0; i < k; i++) { // 循环取出前k个元素
                // 若当前数组已经为空，则跳过
                if (singleArray.isEmpty()) { // 判断当前LinkedList是否为空
                    numArrays.remove(singleArray); // 若为空，则从ArrayList中移除该LinkedList
                    index--; // 索引减1，以便下次循环时可以获取正确的LinkedList
                    break; // 跳出循环
                }
                builder.append(singleArray.removeFirst()).append(","); // 将LinkedList的第一个元素取出并添加到StringBuilder中
            }
            index++; // 索引加1
            if (index >= numArrays.size()) { // 若索引大于等于ArrayList的大小
                index = 0; // 将索引重置为0
            }
        }

        System.out.println(builder.substring(0, builder.length() - 1)); // 输出拼接好的字符串，去掉最后一个逗号
    }

    @Override
    protected void mySolution() {
        System.out.println("数据合并");
        int readCount=scanner.nextInt();//每次读取的元素个数
        int arrayCount = scanner.nextInt();//数组个数
        scanner.nextLine();
        String[][] arr = new String[arrayCount][];
        int maxLen = 0;
        int index = 0;
        while (scanner.hasNextLine() ){
            String[] tmpArr = scanner.nextLine().split(",");
            maxLen = Math.max(tmpArr.length,maxLen);
            arr[index++] = tmpArr;
            if (index == arrayCount){
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        int start = 0;
        int step=readCount;
        while (start<maxLen){
            for (int i=0;i<arr.length;i++){
                String[] item = arr[i];
                for (int j=start;j<item.length && j<(start+step);j++){
                    sb.append(arr[i][j]).append(",");
                }
            }
            start+=step;
        }
        System.out.println(sb.substring(0,sb.length()-1));
    }
}
