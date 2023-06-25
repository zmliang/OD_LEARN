package com.lib.od;

import java.util.Arrays;
import java.util.Comparator;

public class 字符串排序 extends BaseTest{
    @Override
    protected void officialSolution() {
        String[] words = new String[]{};

        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {

                return a.toLowerCase().compareTo(b.toLowerCase());

            }
        });
    }

    @Override
    protected void mySolution() {

    }
}
