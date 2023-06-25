package com.lib.od;


//最少使用多少次a才可以把start变成target
public class 数字加减游戏 extends BaseTest{





    @Override
    protected void officialSolution() {
        int start = scanner.nextInt();
        int target = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        int x = 1;
        int diff = target-start;
        while (true){
            if ((diff-a*x)%b == 0 || (diff+a*x)%b == 0){

                break;
            }
            x++;
        }

    }

    @Override
    protected void mySolution() {

    }
}
