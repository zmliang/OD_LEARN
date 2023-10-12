package com.lib.od;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class shooting_contest extends BaseTest{

    private static boolean isNumber(String str) {
        // 这个代码意思是如果没有抛出异常 就证明是数字，抛出异常了那么就不是数字
        // 异常不适合做逻辑判断，不适合做业务逻辑，异常使用不合理，不符合代码规范
        try {
            // parseInt 是将字符串转换为整数类型，返回一个int类型，如果字符串中有非数字类型字符，则会抛出一个NumberFormatException的异常
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void officialSolution() {

        String input = scanner.nextLine();
        ArrayList<String> operations = new ArrayList<>();
        String operation = "";
        for (char c : input.toCharArray()) {
            if (c == ' ') {
                operations.add(operation);
                operation = "";
            } else {
                operation += c;
            }
        }
        operations.add(operation);
        ArrayList<Integer> scores = new ArrayList<>();

        // 正则表达式匹配整数
        Pattern pattern = Pattern.compile("^-?\\d+$");

        // 遍历操作列表
        for (String op : operations) {
            Matcher matcher = pattern.matcher(op);
            //if (matcher.matches()) { // 如果是整数，将其转换为int类型后加入得分列表
            if (isNumber(op)) {
                scores.add(Integer.parseInt(op));
            } else { // 如果是特殊操作，根据操作类型进行相应处理
                if (op.equals("+")) { // 加法操作，将前两次得分相加并加入得分列表
                    if (scores.size() < 2) { // 如果得分列表中的元素不足两个，输出-1表示无效操作
                        System.out.println(-1);
                        return;
                    } else {
                        scores.add(scores.get(scores.size() - 1) + scores.get(scores.size() - 2));
                    }
                } else if (op.equals("D")) { // 乘法操作，将前一次得分乘二并加入得分列表
                    if (scores.size() < 1) { // 如果得分列表中的元素不足一个，输出-1表示无效操作
                        System.out.println(-1);
                        return;
                    } else {
                        scores.add(scores.get(scores.size() - 1) * 2);
                    }
                } else if (op.equals("C")) { // 清除操作，将得分列表中的最后一个元素删除
                    if (scores.size() < 1) { // 如果得分列表中的元素不足一个，输出-1表示无效操作
                        System.out.println(-1);
                        return;
                    } else {
                        scores.remove(scores.size() - 1);
                    }
                } else { // 如果是其他操作，输出-1表示无效操作
                    System.out.println(-1);
                    return;
                }
            }
        }

        scores.add(0); // 将得分列表末尾加上0，以便计算总得分

        int totalScore = 0;
        for (int score : scores) { // 计算得分列表中所有得分的总和
            totalScore += score;
        }
        System.out.println(totalScore); // 输出总得分

    }




    @Override
    protected void mySolution() {

    }
}
