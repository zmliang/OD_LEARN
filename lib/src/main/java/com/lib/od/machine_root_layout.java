package com.lib.od;

/**
 *
 * 题目描述
 * 小明正在规划一个大型数据中心机房，为了使得机柜上的机器都能正常满负荷工作，需要确保在每个机柜边上至少要有一个电箱。
 * 为了简化题目，假设这个机房是一整排，M表示机柜，l表示间隔，请你返回这整排机柜，至少需要多少个电箱。 如果无解请返回 -1。
 *
 */
public class machine_root_layout extends BaseTest{
    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {
        String cabinetLayout = scanner.nextLine();
        int len = cabinetLayout.length();
        int answer = 0;
        for (int i=0;i<len;i++){
            if (cabinetLayout.charAt(i) == 'M'){
                if (i+1<len && cabinetLayout.charAt(i+1) == 'I'){//当前位置如果是机柜，就尝试纺织右边，
                    answer++;//在右边放电机
                    i+=2;//跳过下一个间隔
                }else if (i-1>=0 && cabinetLayout.charAt(i-1) == 'I'){
                    answer++;
                }else {
                    answer = -1;
                    break;
                }
            }
        }
        System.out.println(answer);
    }
}
