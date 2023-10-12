package com.lib.od;

public class 机房布局 extends BaseTest{
    @Override
    protected void officialSolution() {
        String layout = scanner.nextLine();

        int len = layout.length();

        int result = 0;
        for (int i=0;i<layout.length();i++){
            char c = layout.charAt(i);
            if (i+1<len && layout.charAt(i+1) == 'I'){
                result++;
                i+=2;
            }else if (i-1>=0 && layout.charAt(i-1) == 'I'){
                result++;
            }else {
                result = -1;
                break;
            }
        }
        System.out.println(result);

    }

    @Override
    protected void mySolution() {

    }
}
