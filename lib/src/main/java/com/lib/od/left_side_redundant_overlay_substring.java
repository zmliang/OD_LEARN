package com.lib.od;


/**
 *
 *
 *
 * 题目描述
 * 给定两个字符串s1和s2和正整数K，其中s1长度为n1，s2长度为n2，在s2中选一个子串，满足
 * 。该子串长度为n1+k
 * 。该子串中包含s1中全部字母
 * 。该子串每个字母出现次数不小于s1中对应的字母
 * 我们称s2以长度k几余覆盖s1，给定s1，s2，k，求最左侧的s2以长度k几余覆盖s1的子串的首个元素的下标，如果没有返回**_1**。
 * 输入描述
 * 输入三行，第一行为s1，第二行为s2，第三行为k，s1和s2只包含小写字母
 * 输出描述
 * 最左侧的s2以长度k几余覆盖s1的子串首个元素下标，如果没有返回**_1。**
 *
 */
public class left_side_redundant_overlay_substring extends BaseTest{



    @Override
    protected void mySolution() {
        String target = scanner.next(); // 输入目标字符串
        String source = scanner.next(); // 输入源字符串
        int k = scanner.nextInt(); // 输入最大差异次数

        int targetLen = target.length(); // 目标字符串长度
        int sourceLen = source.length(); // 源字符串长度
        if (sourceLen < targetLen + k) { // 如果源字符串长度小于目标字符串长度加上最大差异次数
            System.out.println(-1); // 则无法完成匹配，输出-1
            return; // 程序结束
        }

        int[] count = new int[128]; // 记录target中每个字符出现的次数，使用ASCII码作为下标

        for (int i = 0; i < targetLen; i++) { // 遍历目标字符串中的每个字符
            int c = target.charAt(i); // 获取当前字符的ASCII码
            count[c]++; // 对应字符出现次数加1
        }

        int total = targetLen; // 记录target中还剩余多少个字符没有匹配

        int maxI = sourceLen - targetLen - k; // 最大匹配次数，即源字符串中需要匹配的子串的个数
        int subStrLen = targetLen + k; // 子串长度，即目标字符串长度加上最大差异次数

        for (int j = 0; j < subStrLen; j++) { // 遍历源字符串中第一个子串中的每个字符
            int c = source.charAt(j); // 获取当前字符的ASCII码

            if (count[c]-- > 0) { // 如果当前字符在目标字符串中出现过，将其出现次数减1
                total--; // 如果该字符是目标字符串中未匹配的字符，则未匹配字符数减1
            }

            if (total == 0) { // 如果未匹配字符数为0，即目标字符串中所有字符均已匹配
                System.out.println(0); // 则输出0
                return; // 程序结束
            }
        }

        for (int i = 1; i <= maxI; i++) { // 遍历源字符串中其余的子串
            int remove = source.charAt(i - 1); // 获取需要移除的字符的ASCII码
            int add = source.charAt(i - 1 + subStrLen); // 获取需要添加的字符的ASCII码

            if (count[remove]++ >= 0) { // 如果需要移除的字符在目标字符串中出现过，将其出现次数加1
                total++; // 如果该字符是目标字符串中未匹配的字符，则未匹配字符数加1
            }

            if (count[add]-- > 0) { // 如果需要添加的字符在目标字符串中出现过，将其出现次数减1
                total--; // 如果该字符是目标字符串中未匹配的字符，则未匹配字符数减1
            }

            if (total == 0) { // 如果未匹配字符数为0，即目标字符串中所有字符均已匹配
                System.out.println(i); // 则输出当前子串的起始位置
                return; // 程序结束
            }
        }

        System.out.println(-1); // 如果无法完成匹配，输出-1
    }


}
