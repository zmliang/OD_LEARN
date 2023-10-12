package com.lib.od.栈;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class 移掉k位数字 {

    public String removeKdigits(String num, int k) {
        if(num.length() == k){
            return "0";
        }
        char[] chars = num.toCharArray();

        Stack<Character> stack = new Stack<>();
        stack.push(chars[0]);
        int count = 0;
        for (int i=1;i< chars.length;i++){
//            if (count == k){
//                while (i< chars.length){
//                    stack.push(chars[i++]);
//                }
//                break;
//            }
            while (!stack.isEmpty() && stack.peek()>chars[i] && count<k){
                count++;
                stack.pop();
            }
            stack.push(chars[i]);
        }
        while (count<k){
            stack.pop();
            count++;
        }
        if (stack.size() == 1){
            return String.valueOf(stack.pop());
        }

        StringBuilder result = new StringBuilder();
        for (char c:stack){
            result.append(c);
        }

        //StringBuilder tmp = result.reverse();
        while (result.charAt(0) == '0' && result.length()>0){
            result.deleteCharAt(0);
            if (result.length() == 1){
                break;
            }
        }

         return result.toString();



    }

}
