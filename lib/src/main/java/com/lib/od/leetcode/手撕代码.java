package com.lib.od.leetcode;

import com.lib.od.链表.ListNode;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class 手撕代码 {
    private boolean isZhiSu(int n){
        if (n == 1 || n ==2){
            return true;
        }
        for (int i=2;i<=Math.sqrt(n);i++){
            if (n%i == 0){
                return false;
            }
        }
        return true;
    }


    /**
     *1-n的 最小公倍数
     */
    public BigInteger leastCommonMultiple(int n){
        BigInteger ret = BigInteger.ONE;
        for (int i=2;i<=n;i++){
            int tmp = 1;
            if (isZhiSu(i)){
               while (tmp*i<=n){
                   tmp*=i;
               }
               System.out.println(i+"--"+tmp);
            }
            ret = ret.multiply(BigInteger.valueOf(tmp));
        }

        return ret;

    }
    public long leastCommonMultiple(int m,int n){

        return (m*n)/(this.gcd(m, n));

    }

    public int gcd(int a,int b){
        int tmpA = Math.max(a,b);
        int tmpB = Math.min(a,b);
        while (tmpB!=0){
            int tmp = tmpA%tmpB;
            tmpA = tmpB;
            tmpB = tmp;
        }
        return tmpA;
    }



    public boolean isIp4(String ip){
        if (ip == null || !ip.contains(".")){
            return false;
        }
        String[] splitStr = ip.split("\\.");
        if (splitStr.length<4){
            return false;
        }
        for (int i=0;i< splitStr.length;i++){
            String item = splitStr[i];
            if (item.trim() == ""){
                return false;
            }
            if (item.startsWith("0")){
                return false;
            }
            if (Integer.parseInt(item)>255){
                return false;
            }
        }
        return true;
    }


    /**
     * 旋转矩阵
     *
     */
    public void rotateMatrix(int[][] matrix){
        int n = matrix.length;
        for (int i=0;i<n/2;i++){
            for (int j=0;j<(n+1)/2;j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][n-i-1];
                matrix[j][n-i-1] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[n-j-1][i];
                matrix[n-j-1][i] = tmp;
            }
        }

    }


    /**
     * 字符串的全排列
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        if (s.equals("") || s.length() == 1){
            return new String[]{s};
        }
        char[] chars = s.toCharArray();
        boolean[] used = new boolean[chars.length];
        Arrays.fill(used,false);
        Set<String> result = new HashSet<>();
        backtrack(chars,0,result,new StringBuilder(),used);

        return result.toArray(new String[0]);

    }

    private void backtrack(char[] chars, int index, Set<String> set, StringBuilder sb,boolean[] used){
        if(chars.length == index){
            set.add(sb.toString());
            return;
        }
        for (int i=0;i< chars.length;i++){
            if (used[i]){
                continue;
            }
            used[i] = true;
            sb.append(chars[i]);
            backtrack(chars, index++, set, sb, used);
            sb.deleteCharAt(i);
            used[i] = false;
        }
    }


    /**
     * 无重复字符最长子串长度
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() <2){
            return s.length();
        }
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;

        int maxLen = Integer.MIN_VALUE;

        while (right<s.length()){
            char c = s.charAt(right);
            right++;
            while (set.contains(c)){
                char tmp = s.charAt(left);
                set.remove(tmp);
                left++;
            }
            set.add(c);
            if (right-left>maxLen){
                maxLen = right-left;
            }
        }
        return maxLen;
    }

    /**
     * 压缩字符串
     * @param s
     * @return
     */
    public String compress(String s) {
        if (s.length()<2){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index<s.length()){
            char c = s.charAt(index);
            int count = 1;
            for (int i=index+1;i<s.length();i++){
                if (c == s.charAt(i)){
                    count++;
                    continue;
                }
                break;
            }
            index+=count;
            sb.append(c).append(count);
        }
        if (s.length()>=sb.length()){
            return s;
        }
        return sb.toString();
    }

    /**
     * 有效括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s.length() <2 || s.length()%2!=0){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        int index = 0;
        while (index<s.length()){
            char c = s.charAt(index++);

            if (c == '{' || c == '[' || c == '('){
                stack.push(c);
            }else {
                if (stack.isEmpty()){
                    return false;
                }
                char top = stack.pop();
                if (c == '}'){
                    if (top!='{'){
                        return false;
                    }
                }else if (c == ']'){
                    if (top!='['){
                        return false;
                    }
                }else if (c == ')'){
                    if (top!='('){
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * 移除掉K位数
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        if (num.length() >= k){
            return "0";
        }
        Stack<Character> stack = new Stack<>();
        int count = 0;
        char[] chars = num.toCharArray();
        stack.push(chars[0]);
        for (int i=1;i< chars.length;i++){
            char c = chars[i];
            while (!stack.isEmpty() && stack.peek()>c && count<k){
                count++;
                stack.pop();
            }
            stack.push(c);
        }
        while (count<k){
            stack.pop();
            count++;
        }

        return new String(stack.toString());
    }

    /**
     * 链表相交
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null){
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        while (a!=b){
            a = (a == null ? headB : a.next);
            b = (b == null ? headA : b.next);
        }

        return a;

    }


    public String addBinary(String a,String b){
        int len = Math.max(a.length(),b.length());
        int carry = 0;
        StringBuilder ret = new StringBuilder();
        for (int i=0;i<len;i++){
            carry+=(i<a.length() ? (a.charAt(a.length()-i-1)-'0') : 0);
            carry+=(i<b.length() ? (b.charAt(b.length()-i-1)-'0') : 0);
            ret.append((carry%8+'0'));
            carry/=8;
        }
        if (carry>0){
            ret.append('1');
        }
        ret.reverse();
        return ret.toString();
    }


    /**
     * 岛屿数量
     * @param grid
     * @return
     */
    private int[][] directions = new int[][]{{0,1},{1,0},{0,-1,},{-1,0}};
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i=0;i< grid.length;i++){
            for (int j=0;j< grid[0].length;j++){
                if (grid[i][j] == '1'){
                    dfs(grid,i,j);
                    count++;
                }
            }
        }
        return count;
    }
    private void dfs(char[][] grid,int row,int column){
        if (row == grid.length || column == grid[0].length){
            return;
        }
        if (row<0 || column<0){
            return;
        }

        if (grid[row][column] != '1'){
            return;
        }
        grid[row][column] = '0';
        for (int[] dir:directions){
            dfs(grid,row+dir[0],column+dir[1]);
        }
    }


    /**
     * 岛屿周长
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int area = 0;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                int land = grid[i][j];
                if (land == 1){
                    area+=dfs(grid,i,j);
                }
            }
        }
        return area;
    }
    public int dfs(int[][] grid,int row,int column){
        if((row>=0 || row<grid.length) || column>=0 || column<grid[0].length || grid[row][column] == 0){
            return 1;
        }
        if (grid[row][column]!=1){//遍历过的就不再重复
            return 0;
        }
        grid[row][column] = 2;
        int ret = 0;
        for(int[] dir:directions){
            ret+=dfs(grid, row+dir[0], column+dir[1]);
        }
        return ret;
    }


    /**
     * 验证回文字符串
     */
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        s = s.toLowerCase();
        for (char c:s.toCharArray()){
            if (Character.isLetterOrDigit(c)){
                sb.append(c);
            }
        }
        for (int i=0;i<sb.length()/2;i++){
            char left = sb.charAt(i);
            char right = sb.charAt(sb.length()-i-1);
            if (left !=right){
                return false;
            }
        }
        return true;
    }


    /**
     * 反转每对括号间的子串
     * @param s
     * @return
     *
     * 输入：s = "(u(love)i)"
     * 输出："iloveu"
     */
    public String reverseParentheses(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c == '('){//开始
                stack.push(sb.toString());
                sb.setLength(0);
            }else if (c == ')'){//结束
                sb.reverse();
                sb.insert(0,stack.pop());
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 计算字符串的数字和
     * @param s
     * @param k
     * @return
     */
    public String digitSum(String s, int k) {
        if (s.length()<=k){
            return s;
        }
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<s.length();i++){
            if (i%k == k){
                sb.append(String.valueOf(count));
                count = 0;
            }
            char c = s.charAt(i);
            count+=(c-'0');
        }
        sb.append(String.valueOf(count));
        return digitSum(sb.toString(), k);
    }


    /**
     * 长度最小的子数组
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int len = nums.length;
        long amount = Arrays.stream(nums).count();
        if(amount<target){
            return 0;
        }
        int left = 0;
        int right = 0;
        int count = 0;
        int minLen = Integer.MAX_VALUE;
        while (right<len){
            int item = nums[right];
            count+=item;
            while (count>=target && left<right){
                minLen = Math.min(minLen,right-left);
                count-=nums[left++];
            }
            right++;
        }
        return minLen;
    }


    /**
     * 字母异位词
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ret = new ArrayList<>();
        for (String item:strs){

        }
        return ret;
    }

}
