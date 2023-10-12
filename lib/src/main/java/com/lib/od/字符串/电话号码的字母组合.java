package com.lib.od.字符串;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor;

public class 电话号码的字母组合 {

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()){
           return result;
        }
        Map<Character,String> map = new HashMap<>();
        map.put('2',"abc");
        map.put('3',"def");
        map.put('4',"ghi");
        map.put('5',"jkl");
        map.put('6',"mno");
        map.put('7',"prs");
        map.put('8',"tuv");
        map.put('9',"wxyz");

        backtrack(result,map,0,digits,new StringBuffer());

        return result;
    }

    public void backtrack(List<String> result,Map<Character,String> map,int index,String digits,StringBuffer tmp){
        if (index == digits.length()){
            result.add(tmp.toString());
            return;
        }
        Character c = digits.charAt(index);
        String word = map.get(c);
        for (int i=0;i<word.length();i++){
            tmp.append(word.charAt(i));
            backtrack(result, map, index+1, digits, tmp);//index+1 表示进行下一个字符的操作
            tmp.deleteCharAt(tmp.length()-1);
        }
    }
}
