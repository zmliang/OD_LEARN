package com.lib.od;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 题目描述
 * 输入一个由随机数组成的数列(数列中每个数均是大于 0 的整数，长度已知》
 * ，和初始计数值 m。从数列首位置开始计数，计数到 m 后，将数列该位置数
 * 值替换计数值 m,并将数列该位置数值出列，然后从下一位置从新开始计数，
 * 直到数列所有数值出列为止。如果计数到达数列尾段，则返回数列首位置继续
 * 计数。请编程实现上述计数过程，同时输出数值出列的顺序
 * 比如: 输入的随机数列为:3,1,2,4，初始计数值 m=7，从数列首位置开始计数
 * (数值 3 所在位置)第一轮计数出列数字为 2，计数值更新 m=2，出列后数列为
 * 3,1.4，从数值 4 所在位置从新开始计数第二轮计数出列数字为 3，计数值更新
 * m=3，出列后数列为 1.4，从数值 1所在位置开始计数第三轮计数出列数字为 1，
 * 计数值更新 m=1，出列后数列为 4，从数值 4 所在位置开始计数最后一轮计数出列
 * 数字为 4，计数过程完成。输出数值出列顺序为: 2.3.1.4。要求实现函数:
 *
 *
 *
 *
 */
public class 约瑟夫问题 extends BaseTest{

    void array_iterate(Integer[] array,int m){
        int len = array.length;
        LinkedList<Integer> dq = new  LinkedList<>(Arrays.asList(array));
        ArrayList ret = new ArrayList();

        int i = 1;//初始化计数器为1
        while (len>0){
            int out = dq.removeFirst();
            if (i == m){
                ret.add(out);
                m = out;
                i = 1;
                len--;
            }else {
                dq.addLast(out);
                i++;
            }
        }
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
