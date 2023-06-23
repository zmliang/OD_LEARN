package com.lib.od;


import com.lib.od.binary_tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目描述
 * 请按下列描述构建一颗二叉树，并返回该树的根节点
 * 1、先创建值为-1的根结点，根节点在第0层;
 * 2、然后根据operations依次添加节点: operationsli] =[height, index]
 * 表示对第height 层的第index 个节点node，添加值为i的子节点:
 * 。若node 无 左子节点] ，则添加左子节点;
 * 。若node 有 T左子节点，，但无 T右子节点]，则添加右子节点;
 * 。否则不作任何处理
 * height、index 均从0开始计数:
 * index 指所在层的创建顺序。
 * 注意:
 * 。输入用例保证每次操作对应的节点已存在;
 * 。控制台输出的内容是根据返回的树根节点，按照层序遍历二叉树打印的结果,
 */
public class 创建二叉树 extends BaseTest{

    public TreeNode createBinaryTree(int[][] operations){
        TreeNode root = new TreeNode();
        root.value = -1;

        ArrayList<TreeNode> level0 = new ArrayList<>();
        level0.add(root);

        ArrayList<ArrayList<TreeNode>> tree = new ArrayList<>();
        tree.add(level0);


        for (int i=0;i<operations.length;i++){
            int height = operations[i][0];
            int index = operations[i][1];
            int value = i;

            //如果当前高度没哟节点，创建一层
            if (tree.size()<=height+1){
                tree.add(new ArrayList<>());
            }
            TreeNode node = new TreeNode();
            node.value = value;

            //添加到新节点种
            TreeNode parent = tree.get(height).get(index);
            if (parent.left == null || parent.right == null){
                tree.get(height+1).add(node);
            }
            if (parent.left == null){
                parent.left = node;
            }else if (parent.right == null){
                parent.right = node;
            }

            //层序遍历树 返回结果

        }


        return root;
    }

    @Override
    protected void officialSolution() {

    }

    @Override
    protected void mySolution() {

    }
}
