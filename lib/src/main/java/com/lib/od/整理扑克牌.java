package com.lib.od;

import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 整理扑克牌 extends BaseTest{

    public String getResult(List<Integer> arr){
        //统计数组中每个数字出现的次数
        Map<Integer,Integer> card = new HashMap<>();
        for (int num:arr){
            int val = card.getOrDefault(num,0);
            card.put(num,++val);
        }
        //定义存储不同组合的容器
        Map<String,List<List<Integer>>> combine = new HashMap<>();
        combine.put("4",new ArrayList<>());
        combine.put("3+2",new ArrayList<>());
        combine.put("3",new ArrayList<>());
        combine.put("2",new ArrayList<>());
        combine.put("1",new ArrayList<>());

        //将数字按照出现次数分组
        for (Map.Entry<Integer,Integer> entry:card.entrySet()){
            int num = entry.getKey();//数字
            int count = entry.getValue();//出现的次数
            switch (count){
                case 3:
                    combine.get("3").add(Arrays.asList(num));
                    break;
                case 2:
                    combine.get("2").add(Arrays.asList(num));
                    break;
                case 1:
                    combine.get("1").add(Arrays.asList(num));
                    break;
                default:
                    combine.get("4").add(Arrays.asList(num,count));
                    break;
            }
        }
        //对四张相同的牌进行排序
        Collections.sort(combine.get("4"), (t1, t2) -> t1.get(1)!=t2.get(1) ? t2.get(1)-t1.get(1) :
                t2.get(0)-t2.get(0));

        //对三张相同的牌进行排序
        Collections.sort(combine.get("3"), (t1, t2) -> t2.get(0)-t1.get(0));

        //对二张相同的牌进行排序
        Collections.sort(combine.get("2"), (t1, t2) -> t1.get(1)!=t2.get(1) ? t2.get(1)-t1.get(1) :
                t2.get(0)-t2.get(0));

        //对一张相同的牌进行排序
        Collections.sort(combine.get("1"), (t1, t2) -> t1.get(1)!=t2.get(1) ? t2.get(1)-t1.get(1) :
                t2.get(0)-t2.get(0));

        //尝试组合成葫芦
        while (combine.get("3").size()>0){
            //如果没有两张相同的牌或者只剩一张三张相同的牌，无法组成葫芦，退出循环
            if (combine.get("2").size() == 0 && combine.get("3").size() == 1){
                break;
            }
            //取出一组三张相同的牌
            int san_top = combine.get("3").get(0).get(0);
            combine.get("3").remove(0);

            //如果还有另外一组三张相同的牌，与之组合成三带二
            int tmp;
            if (combine.get("2").size() == 0 || (combine.get("3").size()>0 &&
                    combine.get("3").get(0).get(0)>combine.get("2").get(0).get(0))){
                tmp = combine.get("3").get(0).get(0);
                combine.get("3").remove(0);
                combine.get("1").add(Arrays.asList(tmp));
            }else {
                tmp = combine.get("2").get(0).get(0);
                combine.get("2").remove(0);
            }
            combine.get("3+2").add(Arrays.asList(san_top,tmp));
        }

        //将不同组合按照顺序组合成最终的牌行
        List<Integer> ans=  new ArrayList<>();
        for (List<Integer> vals:combine.get("4")){
            int score = vals.get(0);
            int count = vals.get(1);
            for (int i=0;i<count;i++){
                ans.add(score);
            }
        }

        for (List<Integer> vals:combine.get("3+2")){
            int score = vals.get(0);
            int count = vals.get(1);
            for (int i=0;i<count;i++){
                ans.add(score);
            }
        }
        for (List<Integer> vals:combine.get("3")){
            for (int i=0;i<3;i++){
                ans.add(vals.get(0));
            }
        }
        for (List<Integer> vals:combine.get("2")){
            for (int i=0;i<2;i++){
                ans.add(vals.get(0));
            }
        }
        for (List<Integer> vals:combine.get("1")){
            ans.add(vals.get(0));
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<ans.size();i++){
            sb.append(ans.get(i));
            if (i !=ans.size()-1){
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
