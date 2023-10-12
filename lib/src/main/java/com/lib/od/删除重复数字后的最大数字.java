package com.lib.od;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class 删除重复数字后的最大数字 extends BaseTest{


    @Override
    protected void officialSolution() {
        String num = scanner.nextLine();
        int k=2;
        HashMap<Character,Integer> usedMap = new HashMap<>();
        HashMap<Character,Integer> unusedMap = new HashMap<>();

        for (char c:num.toCharArray()){
            unusedMap.put(c,unusedMap.getOrDefault(c,0)+1);
            usedMap.put(c,0);
        }

        LinkedList<Character> result = new LinkedList<>();
        for (int i=0;i<num.length();i++){
            char c = num.charAt(i);
            if (usedMap.get(c) == 2){
                unusedMap.put(c,unusedMap.get(c)-1);
                continue;
            }

            while (!result.isEmpty()){
                char top = result.getLast();
                if (top<c && unusedMap.get(top)+usedMap.get(top)-1>=2){
                    result.removeLast();
                    usedMap.put(top,usedMap.get(top)-1);
                }else {
                    break;
                }
            }


            result.add(c);

            usedMap.put(c,usedMap.get(c)+1);
            unusedMap.put(c,unusedMap.get(c)-1);
        }


    }

    @Override
    protected void mySolution() {

    }
}
