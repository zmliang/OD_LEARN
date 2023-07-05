package com.lib.od.栈;

import com.lib.od.binary_tree.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class 二叉树展开为链表 {

    public void flatten(TreeNode root) {
        if (root == null){
            return;
        }
        TreeNode p = root;
        Deque<TreeNode> stack = new LinkedList<>();
        List<TreeNode> list = new ArrayList<>();
        while (p!=null && !stack.isEmpty()){
            while (p!=null){
                list.add(p);
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            p = p.right;
        }
        int size = list.size();
        for (int i=1;i<size;i++){
            TreeNode pre = list.get(i-1);
            TreeNode cur = list.get(i);
            pre.right = cur;
            pre.left = null;
        }
    }

    public void createTree(){
        Integer[] node = new Integer[]{1,2,5,3,4,null,6};

        TreeNode root = new TreeNode();
        TreeNode p = root;
        Deque<TreeNode> stack = new LinkedList<>();
        List<TreeNode> list = new ArrayList<>();
        while (p!=null && !stack.isEmpty()){
           while (p!=null){
               list.add(p);
               stack.push(p);
               p = p.left;
           }
           p = stack.pop();
           p = p.right;
        }

    }



}
