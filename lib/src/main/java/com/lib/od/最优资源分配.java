package com.lib.od;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目描述
 * 某块业务芯片最小容量单位为1.25G，总容量为M*1.25G，对该芯片资源编号为1，2，...，M。该芯片支持3种不同的配置，分别为A、B、C。
 * 。配置A: 占用容量为 1.25 *1=1.25G
 * 配置B: 占用容量为 1.25*2 =2.5G
 * 配置C: 占用容量为 1.25*8 = 10G。
 * 某块板卡上集成了N块上述芯片，对芯片编号为1，2，...，N，各个芯片之间彼此独立，不能跨芯片占用资源。
 * 给定板卡上芯片数量N、每块芯片容量M、用户按次序配置后，请输出芯片资源占用情况，保证消耗的芯片数量最少。
 * 资源分配规则: 按照芯片编号从小到大分配所需资源，芯片上资源如果被占用标记为1，没有被占用标记为0.
 * 用户配置序列: 用户配置是按次序依次配置到芯片中，如果用户配置序列种某个配置超过了芯片总容量，丢弃该配置，继续遍历用户后续配置。
 *
 *题目解析
 * 以用例1解释一下:
 * 用例1的前两行输入表示
 * 板卡上有N=2个芯片，而每个芯片有8个单位容量，因此对应如下
 * 00000000
 * 00000000
 * 其中每个0代表一个单位容量，而一个芯片有8单位容量，因此第一排8个0代表一个芯片的总容量，第二排8个0代表另一个芯片的总容量。
 * 代码思路
 * 首先，读入每块芯片的容量和板卡上芯片的数量，以及用户配置序列。然后，初始化每块板卡的总容量为芯片数量“芯片容量。接下来，
 * 定义一个字典，将用户配置序列中的字符映射到对应的容量。遍历用户配置序列，按照芯片编号从小到大分配所需资源。具体地，
 * 对于每个配置，遍历每块板卡，找到第一个容量足够的板卡，将其对应的总容量减去所需资源。最后，输出每块芯片的占用情况。
 * 对于每块芯片，计算已用和未用容量，构造字符串表示每块芯片的占用情况。
 *
 */
public class 最优资源分配 extends BaseTest{


    @Override
    protected void officialSolution() {
        int chip_capacity = scanner.nextInt();
        int chip_count = scanner.nextInt();
        String sequence = scanner.next();
        //初始化每块卡板的总容量
        List<Double> borad_card = new ArrayList<>(Collections.nCopies(chip_count,chip_capacity*1.25));

        //配置规则
        Map<Character,Integer> dict = new HashMap<>();
        dict.put('A',1);
        dict.put('B',2);
        dict.put('C',8);

        //遍历用户配置序列，
        for (int i=0;i<sequence.length();i++){
            double need = 1.25*dict.get(sequence.charAt(i));
            for (int j=0;j<chip_count;j++){
                if (borad_card.get(j)>=need){
                    borad_card.set(j,borad_card.get(j)-need);
                    break;
                }
            }
        }
        //输出每块芯片的占用情况
        for (int i=0;i<chip_count;i++){
            int un_used = (int) (borad_card.get(i)/1.25);
            int used = chip_capacity-un_used;

            StringBuilder sb = new StringBuilder();
            for (int j=0;j<used;j++){
                sb.append("1");
            }
            for (int k=0;k<un_used;k++){
                sb.append("0");
            }
            System.out.println(sb.toString());
        }

    }

    @Override
    protected void mySolution() {

    }
}





















