package com.lib.od.binary_tree;

import com.lib.od.BaseTest;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

public class BinaryTree extends BaseTest {
    Random random = new Random();

    public TreeNode create(){
        TreeNode root= new TreeNode();
        for (int i=0;i<10;i++){
            root.value = i;
            TreeNode node = new TreeNode();
            root.left = node;
            root = node;

        }

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.ceiling("");
        return null;
    }


    public void preOrder(TreeNode root){
        if (root == null){
            return;
        }
        System.out.println(root.value);
        preOrder(root.left);
        preOrder(root.right);
    }

    public void preOrder_(TreeNode root){
        if (root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty()){
            while (root!=null){
                System.out.println(root.value);
                stack.push(root);
                root = root.left;
            }
            TreeNode node = stack.pop();
            stack.push(node.right);
        }
    }



    /**
     * 二叉树的遍历
     */


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
