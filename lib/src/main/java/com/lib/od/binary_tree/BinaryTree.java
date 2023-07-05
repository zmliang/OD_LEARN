package com.lib.od.binary_tree;

import com.lib.od.BaseTest;

import java.util.Random;
import java.util.Stack;

public class BinaryTree extends BaseTest {
    Random random = new Random();

    public void perOrder(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){

            TreeNode node = stack.pop();
            if (node.left!=null){
                stack.push(node.left);
            }

            if (node.right!=null){
                stack.push(node.right);
            }
        }



    }


    private TreeNode create(TreeNode root,int depth){
        if (depth == 10){
            return root;
        }
        root.value = random.nextInt();
        root.left = new TreeNode();
        root.right = new TreeNode();
        create(root.right,depth);
        return root;
    }

    private void traseve(TreeNode root){

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
