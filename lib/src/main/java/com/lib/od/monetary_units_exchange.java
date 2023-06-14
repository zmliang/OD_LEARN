package com.lib.od;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class monetary_units_exchange extends BaseTest{




    @Override
    void officialSolution() {
        int n = scanner.nextInt();

        // 创建字符串数组，存储输入的货币字符串
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.next();
        }

        // 正则表达式匹配货币金额
        String pattern_str = "(\\d+)((CNY)|(JPY)|(HKD)|(EUR)|(GBP)|(fen)|(cents)|(sen)|(eurocents)|(pence))";
        Pattern pattern = Pattern.compile(pattern_str);

        // 创建哈希表，存储货币单位对应的汇率
        HashMap<String, Double> exchange_rate = new HashMap<>();
        exchange_rate.put("CNY", 100.0);
        exchange_rate.put("JPY", 100.0 / 1825 * 100);
        exchange_rate.put("HKD", 100.0 / 123 * 100);
        exchange_rate.put("EUR", 100.0 / 14 * 100);
        exchange_rate.put("GBP", 100.0 / 12 * 100);
        exchange_rate.put("fen", 1.0);
        exchange_rate.put("cents", 100.0 / 123);
        exchange_rate.put("sen", 100.0 / 1825);
        exchange_rate.put("eurocents", 100.0 / 14);
        exchange_rate.put("pence", 100.0 / 12);

        // 计算总共的货币数量（以分为单位）
        double total_fen = 0.0;
        for (int i = 0; i < n; i++) {
            // 使用正则表达式匹配货币字符串中的金额和单位
            Matcher matcher = pattern.matcher(arr[i]);
            while (matcher.find()) {
                double amount = Double.parseDouble(matcher.group(1)); // 将金额字符串转换为 double 类型
                String unit = matcher.group(2);
                total_fen += amount * exchange_rate.get(unit); // 计算该货币的总共金额（以分为单位）
            }
        }

        // 输出总共的货币数量（以分为单位）
        System.out.println((int) total_fen);
    }

    @Override
    protected void mySolution() {
        Pattern pattern = Pattern.compile("(\\d+)((CNY)|(JPY))");
    }
}
