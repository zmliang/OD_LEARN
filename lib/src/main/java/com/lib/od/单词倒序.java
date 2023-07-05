package com.lib.od;


/**
 * 题目描述
 * 输入单行英文句子，里面包含英文字母，空格以及.?二种标点符号，请将句子内每个单词进行倒序并输出倒序后的语句。
 * 输入描述
 * 输入字符串S，S的长度 1<N< 100
 *
 *
 * 题目解析
 * 将字符串单词转为字符数组后进行reverse。但是单词中如果有标点符号的话，则标点符号的位置不能改变，比如enif.
 * 倒序后为 fine.其中.的位置在倒序前后是一样的。
 * 从左到右遍历每一个字符，如果字符是，.?或者空格，则看成一个分界符，将分界符之间的单词片段进行倒序。
 */
public class 单词倒序 extends BaseTest{

    public static String reverse_string(String str){
        String res = "";
        for (int i=str.length()-1;i>=0;i--){
            res+=str.charAt(i);
        }
        return res;
    }

    @Override
    protected void officialSolution() {
        String input_str = scanner.nextLine();
        String res = "";
        String temp = "";

        for (int i=0;i<input_str.length();i++){
            char c = input_str.charAt(i);
            if (Character.isLetter(c)){
                temp+=c;
            }else if (c == ' '){
                res+=reverse_string(temp)+" ";
                temp = "";
            }else{
                if (!temp.equals("")){
                    res+=reverse_string(temp);
                    temp = "";
                }
                res+=c;
            }
            if (i == input_str.length()-1 && temp.equals("")){
                res+=reverse_string(temp);
            }
        }
        System.out.println(res);
    }


    public void mySolution() {
        String words = scanner.nextLine();
        char[] chars = words.toCharArray();
        StringBuilder tmp =  new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i< chars.length;i++){
            char c = chars[i];
            if (c == ' ' || c == '.' || (c == ',' || c == '?')){
                sb.append(tmp.reverse().append(c));
                tmp = new StringBuilder();
            }else {//字母单词
                tmp.append(c);
            }
        }
        System.out.println(sb);
    }
}




















