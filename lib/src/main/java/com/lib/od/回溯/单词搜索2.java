package com.lib.od.回溯;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class 单词搜索2 {

    public int[][] directions = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    public List<String> findWords(char[][] board, String[] words) {
        List<String> wordList = Arrays.stream(words).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                if (i>=0 && i< board.length
                &&j>=0 &&j<board[0].length){
                    backtrack(board,wordList,new StringBuffer(),result);
                }
            }
        }
        return result;
    }


    void backtrack(char[][] board,  List<String> wordList,StringBuffer tmp,List<String> result){
        if (wordList.size() == 0){
            return;
        }
        if (wordList.contains(tmp.toString())){
            wordList.remove(tmp.toString());
            result.add(tmp.toString());
            return;
        }

        for (int[] direction:directions){

        }
    }

}
