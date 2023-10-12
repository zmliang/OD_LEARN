package com.lib.od;


import java.util.ArrayList;
import java.util.List;

/**
 * 最差产品奖
 *
 *
 *
 */
public class worst_product_award extends BaseTest{
    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {
        int m = scanner.nextInt();
        scanner.nextLine();
        String[] line = scanner.nextLine().split(",");
        int[] tokens = new int[line.length];
        for (int i=0;i< line.length-m;i++){
            tokens[i] = Integer.parseInt(line[i]);
        }

        List<Integer> worst = new ArrayList<>();
        for (int i=0;i<= tokens.length;i++){
            int worst_value=Integer.MAX_VALUE;
            for (int j=i;j<m;j++){
                worst_value = Math.min(worst_value,tokens[j]);
            }
            worst.add(worst_value);
        }

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<worst.size();i++){
            sb.append(worst.get(i));
            if (i!=worst.size()-1){
                sb.append(",");
            }
        }

        System.out.println(sb.toString());

    }
}
