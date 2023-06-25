package com.lib.od;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class 最长回文字符串 extends BaseTest{

//    public static String getResult(String[] words){
//        Arrays.sort(words,(a,b)-> a.toLowerCase().compareTo(b.toLowerCase()));
//
//        LinkedList<String> ret = new LinkedList<>();
//        ret.add(words[0]);
//        for (int i=0;i<words.length;i++){
//            String word = words[i];
//            String tmp = word.toLowerCase();
//            if (tmp.equals(ret.getLast().toLowerCase())){
//                continue;
//            }
//            ret.add(word);
//        }
//
//    }

    @Override
    protected void officialSolution() {
        String str = scanner.nextLine();
        Map<Character,Integer> map = new HashMap<>();
        for (char c:str.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }

        String middleStr = "";
        StringBuilder sb = new StringBuilder();
        for (Character c: map.keySet()){
            int count = map.get(c);
            if (count>=2){
                for (int i=0;i<count/2;i++){
                    sb.append(c);
                }
            }
            if (count%2!=0 && (middleStr == "" || middleStr.compareTo(c+"")>0)){
                middleStr+=c;
            }
        }
        //sb.toString().toCharArray().
    }

    @Override
    protected void mySolution() {

    }
}
