package com.lib.od.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class 打开转盘锁 {

    public int openLock(String[] deadends, String target) {

        Set<String> deadSet = new HashSet<>(Arrays.stream(deadends).collect(Collectors.toList()));
        if(deadSet.contains("0000") || deadSet.contains(target)){
            return -1;
        }


        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");

        Set<String> visited = new HashSet<>();
        visited.add("0000");

        int step = 0;
        while (queue.size()!=0){
            //step++;
            int size = queue.size();
            for (int i=0;i<size;i++){
                String cur = queue.poll();
                if (cur.equals(target)){
                    return step;
                }
                List<String> children = nextLock(cur);
                for (String child:children){
                    if (visited.contains(child) || deadSet.contains(child)){
                        continue;
                    }
                    queue.offer(child);
                    visited.add(child);
                }

            }
            step++;
        }

        return -1;
    }

    public char nextNum(char c,boolean prev){
        if (prev){
            return c == '0' ? '9' : --c;
        }
        return c == '9' ? '0' : ++c;
    }



    public List<String> nextLock(String cur){
        char[] chars = cur.toCharArray();
        List<String> ret = new ArrayList<>();
        for (int i=0;i<4;i++){
            char c = chars[i];
            chars[i] = nextNum(c,true);
            ret.add(new String(chars));
            chars[i] = nextNum(c,false);
            ret.add(new String(chars));
            chars[i] = c;
        }
        return ret;
    }


}
