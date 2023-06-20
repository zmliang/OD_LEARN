package com.lib.od.binary_tree;

import com.lib.od.BaseTest;

import java.util.Random;

public class BinaryTree extends BaseTest {
    Random random = new Random();


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
