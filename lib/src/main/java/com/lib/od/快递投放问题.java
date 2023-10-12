package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class 快递投放问题 extends BaseTest{

    public List<String> getResult(String[][] packageInfos,String[][] pointInfos){
        Map<String, HashSet<String>> packageMap = new HashMap<>();
        for (String[] _package:packageInfos){
            String key = _package[1]+_package[2];
            packageMap.putIfAbsent(key,new HashSet<>());
            packageMap.get(key).add(_package[0]);//key所表示的两个站点之间，需要通过的包裹集合
        }
        Map<String,HashSet<String>> pointMap = new HashMap<>();
        for (String[] point:pointInfos){
            String key = point[0]+point[1];
            pointMap.putIfAbsent(key,new HashSet<>());
            pointMap.get(key).addAll(Arrays.asList(Arrays.copyOfRange(point,2,point.length)));//两个站点之间不允许通过的包裹集合
        }
        List<String> result = new ArrayList<>();
        for (String key:packageMap.keySet()){
            HashSet<String> packs = packageMap.get(key);
            HashSet<String> forb = pointMap.get(key);
            if (forb == null){
                continue;
            }
            for (String pack:packs){
                if (forb.contains(pack)){
                    result.add(pack);
                }
            }
        }
        return result;
    }



    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
