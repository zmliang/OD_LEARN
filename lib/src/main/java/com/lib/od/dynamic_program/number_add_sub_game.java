package com.lib.od.dynamic_program;

import com.lib.od.BaseTest;

public class number_add_sub_game extends BaseTest {
    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {
        int s = scanner.nextInt();
        int t = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        int ret = 0;
        int diff = t-s;
        while (true){
            if ((diff-a*ret)%b == 0 || (diff+a*ret)%b == 0){//对于方程式 a*x+b*y = s；找到解
                System.out.println(Math.abs(ret));
                return;
            }
            ret++;
        }

    }
}
