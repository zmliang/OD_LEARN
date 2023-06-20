package com.lib.od;

import java.util.HashMap;

/**
 * 题目描述
 * 在第一人称射击游戏中，玩家通过键盘的A、S、D、W四个按键控制游戏人物分别向左、向后、向右、向前进行移动，
 * 从而完成走位假设玩家每按动一次键盘，游戏任务会向某个方向移动一步，如果玩家在操作一定次数的键盘并目各
 * 个方向的步数相同时，此时游戏任条必定会回到原点，则称此次走位为完美走位。
 * 现给定玩家的走位(例如:ASDA)，请通过更换其中一段连续走位的方式使得原走位能够变成一个完美走位。其中待
 * 更换的连续走位可以是相同长度的任何走位.
 * 请返回待更换的连续走位的最小可能长度
 * 如果原走位本身是一个完美走位，则返回0
 * 输入描述
 * 输入为由键盘字母表示的走位s，例如: ASDA
 * 说明
 * 1、走位长度 1≤s.length < 100000 (也就是长度不一定是偶数)2、s.length 是 4 的倍数
 * 3、s中只含有'A，S'，D'，W' 四种字符
 * 输出描述
 * 输出为待更换的连续走位的最小可能长度
 *
 * 题目解析
 * 分析
 * 完美走位是上下左右的次数一样的。如果不一样，我们需要在给定的走位中，找到一个连续的字串，替换走位，
 * 是的走位完美题目要求，保持W.A.S.D字母个数平衡，即相等，如果不相等，可以从字符串中选取一段连续子串替换，来让字符串平衡。
 * 比如:WWWWAAAASSSS
 * 字符串长度12，W.AS,D平衡的话，则每个字母个数应该是3个，而现在W.A.S各有4个，也就是说各超了1个。
 * 因此我们应该从字符串中，选取一段包含1个W，1个A，1个S的子串，来替换为D.
 * WWWWAAAASSSS
 * 而符合这种要求的子串可能很多，我们需要找出其中最短的，即WAAAAS.
 *
 *
 * 代码思路
 * 这道题是一个滑动窗口的问题，需要找到一个最短的子串，使得该子串中每个字符出现的次数都不超过字符串总长度的四分之一。
 * 如果原字符串本身就是一个完美走位，则直接返回0。
 * 首先、我们可以用一个无序映射来记录每个字符出现的次数，然后遍历一遍字符串，统计每个字符出现的次数。接着。我们可以
 * 计算出每个字符出现次数的平均值，如果有某个字符出现的次数超过了平均值，则说明这个字符需要被调整，我们可以将超出平均
 * 值的数量累加起来，用于后面的计算。
 * 接下来，我们使用双指针来寻找最短的平衡子串。我们将右指针向右移动，每次更新无序映射中对应字符的出现次数，并判断是否超过，
 * 平均值。如果超过了平均值，则将超出平均值的数量累加到total中。当total为0时，说明当前的子串已经是一个平衡子串了，我们
 * 可以更新最短子串的长度，并将左指针向右移动，直到子串不再平衡为止。最后，输出最短的平衡子串的长度即可。
 * 需要注意的是，如果原字符串本身就是一个完美走位，则直接返回0。
 *
 */
public class 完美走位 extends BaseTest{

    @Override
    protected void officialSolution() {
        String s = scanner.next();
        HashMap<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int avg = s.length() / 4;
        int total = 0;
        boolean is_balanced = true;
        for (char c : counts.keySet()) {
            int count = counts.get(c);
            if (count > avg) {
                is_balanced = false;
                counts.put(c, count - avg);
                total += count - avg;
            } else {
                counts.put(c, 0);
            }
        }
        if (is_balanced) {
            System.out.println(0);
            return;
        }
        int left = 0;
        int right = 0;
        int min_len = s.length() + 1;
        while (right < s.length()) {
            char rc = s.charAt(right);
            if (counts.get(rc) > 0) {
                total--;
            }
            counts.put(rc, counts.get(rc) - 1);
            while (total == 0) {
                min_len = Math.min(min_len, right - left + 1);
                char lc = s.charAt(left);
                if (counts.get(lc) >= 0) {
                    total++;
                }
                counts.put(lc, counts.get(lc) + 1);
                left++;
            }
            right++;
        }
        System.out.println(min_len);
    }

    @Override
    protected void mySolution() {

    }
}
