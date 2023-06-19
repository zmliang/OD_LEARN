package com.lib.od;

import java.util.List;

public class 羊_狼_农夫过河 extends BaseTest{

    public static void dfs(int sheep, int wolf, int bot, int oppo_sheep,
                           int oppo_wolf, int count, List<Integer> ans){
        if (sheep == 0 && wolf == 0){
            ans.add(count+1);
            return;
        }
        if (sheep+wolf<=bot){
            ans.add(count+1);
            return;
        }
        for (int i=0;i<Math.min(bot,sheep);i++){
            for (int j=0;j<Math.min(bot,wolf);j++){
                if (i+j == 0){
                    continue;
                }
                if (i+j>bot){
                    break;
                }
                if (sheep-i<=wolf-j && sheep-i!=0){//如果羊的数量小于狼的数量，跳过
                    continue;
                }
                if (oppo_sheep+i<=oppo_wolf+j&&oppo_sheep+i!=0){//如果对岸羊小于狼，跳过
                    break;
                }
                if (oppo_sheep+i==0&&oppo_wolf+j>bot){//如果对岸没有羊，但是对岸狼大于等于船的数量，则跳过
                    break;
                }
                dfs(sheep-i,wolf-j,bot,oppo_sheep+i,oppo_wolf+j,count+1,ans);
            }
        }

    }

    @Override
    void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}






















