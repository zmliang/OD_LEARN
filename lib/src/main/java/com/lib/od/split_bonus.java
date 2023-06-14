package com.lib.od;


import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * 分奖金
 *
 *题目描述
 * 公司老板做了一笔大生意，想要给每位员工分配一些奖金，想通过游戏的方式来决定每个人分多少钱。
 * 按照员工的工号顺序，每个人随机抽取一个数字。
 * 按照工号的顺序往后排列，遇到第一个数字比自己数字大的，那么，前面的员工就可以获得“距离*数字差值”的奖金.
 * 如果遇不到比自己数字大的，就给自己分配 随机数Q数量的奖金。
 * 例如，按照工号顺序的随机数字是: 2.10.3。
 * 那么第2个员工的数字10比第1个员工的数字2大,
 * 所以，第1个员工可以获得1*(10-2) =8。第2个员工后面没有比他数字更大的员工所以，他获得他分配的随机数数量的奖金，就是10。第3个员工是最后一个员工，后面也没有比他更大数字的员工，所以他得到的奖金是3.
 * 请帮老板计算一下每位员工最终分到的奖金都是多少钱。
 * 输入描述
 * 第一行n表示员工数量 (包含最后一个老板)
 * 第二是每位员工分配的随机数字
 * 输出描述
 * 最终每位员工分到的奖金数量
 * 注: 随数字不重复，员工数量 (包含老板) 范围110000，随机数范围1100000
 *
 *
 */
public class split_bonus extends BaseTest{



    @Override
    void officialSolution() {
        int employeeNum = scanner.nextInt(); // 读取用户输入的员工数量

        int[] randomNumbers = new int[employeeNum]; // 创建一个长度为员工数量的整型数组
        for (int i = 0; i < employeeNum; i++) { // 循环读取用户输入的每个员工的随机数
            randomNumbers[i] = scanner.nextInt();
        }

        ArrayList<Integer> bonusList = new ArrayList<>(); // 创建一个 ArrayList 对象，用于存储每个员工的奖金金额


        outer: // 标签，用于跳出多层循环
        for (int i = 0; i < employeeNum; i++) { // 循环每个员工
            for (int j = i + 1; j < employeeNum; j++) { // 循环比当前员工编号大的员工
                if (randomNumbers[j] > randomNumbers[i]) { // 如果比当前员工的随机数大
                    bonusList.add((j - i) * (randomNumbers[j] - randomNumbers[i])); // 计算奖金金额并添加到 ArrayList 中
                    continue outer; // 跳出外层循环，继续处理下一个员工
                }
            }
            bonusList.add(randomNumbers[i]); // 如果没有比当前员工的随机数大的员工，则只能得到自己的随机数对应的奖金金额
        }

        StringJoiner sj = new StringJoiner(" "); // 创建一个 StringJoiner 对象，用于拼接字符串
        for (Integer bonus : bonusList) { // 循环 ArrayList 中的每个奖金金额
            sj.add(bonus + ""); // 将奖金金额转换为字符串并添加到 StringJoiner 中
        }

        System.out.println(sj.toString()); // 输出拼接好的字符串
    }

    @Override
    protected void mySolution() {

    }
}
