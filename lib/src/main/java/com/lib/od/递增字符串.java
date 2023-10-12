package com.lib.od;

public class 递增字符串 extends BaseTest{
    @Override
    protected void officialSolution() {
        String str = scanner.next();
        int count_a = 0;
        for (char c:str.toCharArray()){
            if (c == 'A'){
                count_a++;
            }
        }
        if (count_a == 0 || count_a == str.length()){
            System.out.println(0);
            return;
        }
        int left_a = 0;
        int ret = Integer.MAX_VALUE;
        for (int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if (c == 'A'){
                left_a++;
            }
            ret = Math.min(ret,i+1-left_a+count_a-left_a);
        }
    }

    @Override
    protected void mySolution() {
        String str = new String();
        int countA= 0;
        for (char c:str.toCharArray()){
            if (c == 'A'){
                countA++;
            }
        }
        int leftA = 0;
        int ret = Integer.MAX_VALUE;
        for (int i=0;i<str.length();i++){
            if (str.charAt(i) == 'A'){
                leftA++;
            }
            ret = Math.min(ret,(i+1-leftA+(countA-leftA)));
        }

    }
}
