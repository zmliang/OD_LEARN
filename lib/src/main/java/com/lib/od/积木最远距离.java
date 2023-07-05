package com.lib.od;

import java.util.HashMap;
import java.util.Map;

public class 积木最远距离 {
    public int longDis(int[] array){
        int n = array.length;
        Map<Integer,Integer> map = new HashMap<>();
        int maxDis =-1;
        for (int i=0;i<n;i++){
            int val = map.getOrDefault(array[i],-1);
            if (val!=-1){
                maxDis = Math.max(maxDis,i-val);
            }else {
                map.put(array[i],i);
            }
        }
        return maxDis;
    }
}
