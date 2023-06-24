package com.lib.od;

import java.util.HashMap;
import java.util.Map;

public class 发现新词的数量 extends BaseTest{

    public int getResult(String content,String word){
        Map<Character,Integer> charCountMap = new HashMap<>();
        for (char c:word.toCharArray()){
            charCountMap.put(c,charCountMap.getOrDefault(c,0)+1);
        }
        int ret = 0;
        int winSize = word.length();
        for (int i=0;i<content.length();i++){
            char left = content.charAt(i);
            char right = content.charAt(i+winSize-1);
            if (charCountMap.containsKey(left)){
                charCountMap.put(left,charCountMap.getOrDefault(left,0)-1);
                if (charCountMap.get(left)>=0){
                    winSize--;
                }
            }
            if (charCountMap.containsKey(right)){
                charCountMap.put(right,charCountMap.getOrDefault(right,0)-1);
                if (charCountMap.get(right)>=0){
                    winSize--;
                }
            }
            if (winSize == 0){
                ret++;
            }

            if (charCountMap.containsKey(left)){
                charCountMap.put(left,charCountMap.getOrDefault(left,0)+1);
                if (charCountMap.get(left)>0){
                    winSize++;
                }
            }
        }
        return ret;
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
