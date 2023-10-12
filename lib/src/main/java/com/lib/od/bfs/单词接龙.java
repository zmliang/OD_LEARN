package com.lib.od.bfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class 单词接龙 {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);
        if (set.size() == 0 || !set.contains(endWord)){
            return 0;
        }
        //图的广度优先遍历，使用队列和一个表示已访问过的visited
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int step = 0;
        while (queue.size()!=0){
            int size = queue.size();
            for (int i=0;i<size;i++){
                String word = queue.poll();
                if (bfs(word,endWord,visited,set,queue)){
                    step++;
                }

            }

        }
        return step;
    }


    public boolean bfs(String beginWord,String endWord,Set<String> visited,Set<String> wordSet,Queue<String> queue){
        char[] charArray = beginWord.toCharArray();
        for (int i = 0; i < endWord.length(); i++) {
            // 先保存，然后恢复
            char originChar = charArray[i];
            for (char k = 'a'; k <= 'z'; k++) {
                if (k == originChar) {
                    continue;
                }
                charArray[i] = k;
                String nextWord = String.valueOf(charArray);
                if (wordSet.contains(nextWord)) {
                    if (nextWord.equals(endWord)) {
                        return true;
                    }
                    if (!visited.contains(nextWord)) {
                        queue.add(nextWord);
                        // 注意：添加到队列以后，必须马上标记为已经访问
                        visited.add(nextWord);
                    }
                }
            }
            // 恢复
            charArray[i] = originChar;
        }
        return false;
    }






}
