package com.lib.od.bfs;

import com.lib.od.BaseTest;
import com.lib.od.binary_tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class 二叉树的层序遍历 extends BaseTest {

    public List<List<Integer>> bfsTraversal(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null){
            return ret;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            List<Integer> list = new ArrayList<>();
            //TreeNode node = queue.poll();
            for (int i=0;i<queue.size();i++){
                TreeNode node = queue.poll();
                list.add(node.value);
                if (node.left!=null){
                    queue.offer(node.left);
                }
                if (node.right!=null){
                    queue.offer(node.right);
                }
            }
            ret.add(list);
        }

        return ret;
    }


    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
