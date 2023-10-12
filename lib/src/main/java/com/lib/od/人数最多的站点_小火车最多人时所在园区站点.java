package com.lib.od;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class 人数最多的站点_小火车最多人时所在园区站点 extends BaseTest{

        int getResult(List<List<Integer>> stages){

            int ret = 0;

            Map<Integer,Integer> countMap = new HashMap<>();

            int count = stages.size();
            for (List<Integer> item:stages){
                int start = item.get(0);
                int end = item.get(1);
                for (int i=start;i<=end;i++){
                    countMap.put(i,countMap.getOrDefault(i,0)+1);
                }
            }

            countMap.keySet().stream().collect(Collectors.toList()).sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer t1) {
                    return 0;
                }
            });

            return ret;
        }
    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
