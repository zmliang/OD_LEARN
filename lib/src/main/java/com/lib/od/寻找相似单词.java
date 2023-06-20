package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class 寻找相似单词 extends BaseTest{

    public static String sortWord(String word){
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    @Override
    protected void officialSolution() {
        int n = scanner.nextInt();

        //存储所有单词
        ArrayList<String> words = new ArrayList<>();
        for (int i=0;i<n;i++){
            words.add(scanner.next());
        }
        String target = scanner.next();
        String sortedTarget = sortWord(target);

        ArrayList<String> similarWords = new ArrayList<>();
        for (String word:words){
            String sorted_word = sortWord(word);
            if (sortedTarget.equals(sorted_word)){
                similarWords.add(word);
            }
        }
        if (similarWords.size()>0){
            //字典排序
            Collections.sort(similarWords);
            for (String item:similarWords){
                System.out.print(similarWords+" ");
            }
            System.out.println();
        }else {
            System.out.println("null");
        }
    }

    @Override
    protected void mySolution() {

    }
}
