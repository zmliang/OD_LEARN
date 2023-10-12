package com.lib.od.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 猜数字游戏 {

    public String getHint(String secret, String guess) {

        int bulls = 0;
        int cows = 0;
        int[] countSecret = new int[10];
        int[] countGuess = new int[10];

        for (int i=0;i<secret.length();i++){
            if (secret.charAt(i) == guess.charAt(i)){
                bulls++;
            }else {
                countSecret[secret.charAt(i)-'0']++;
                countGuess[guess.charAt(i)-'0']++;
            }
        }

        for (int i=0;i<countSecret.length;i++){
            cows+=Math.min(countSecret[i], countGuess[i]);
        }


        return bulls+"A"+cows+"B";

    }

}
