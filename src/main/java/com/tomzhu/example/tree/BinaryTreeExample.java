package com.tomzhu.example.tree;

import com.tomzhu.tree.BinaryTree;

/**
 * Created by tomzhu on 17-7-17.
 */
public class BinaryTreeExample {

    /**
     * create a binary tree :
     *                              5
     *                            /   \
     *                           3    9
     *                         /  \  / \
     *                        2   4 7  10
     *                             /
     *                            6
     * and then print it.
     * @param args
     */
    public static void main(String args[]) {
        BinaryTree<Integer> binaryTree = new BinaryTree<Integer>(5);
        binaryTree.addLeftChild(binaryTree.getRoot() , 3).addLeftChild(2);
        binaryTree.getLeftChild(binaryTree.getRoot()).addRightChild(4);
        binaryTree.addRightChild(binaryTree.getRoot() , 9).addLeftChild(7).addLeftChild(6);
        binaryTree.getRightChild(binaryTree.getRoot()).addRightChild(10);
        System.out.println("root : " + binaryTree.getRoot().getValue());
        System.out.println("layer 1 : " + binaryTree.getRoot().getLeftChild().getValue() + " , " +
        binaryTree.getRoot().getRightChild().getValue());

        System.out.println("layer 2 : " + binaryTree.getRoot().getLeftChild().getLeftChild().getValue()
            + " , " + binaryTree.getRoot().getLeftChild().getRightChild().getValue() + " | " +
                binaryTree.getRoot().getRightChild().getLeftChild().getValue() + " , " +
                binaryTree.getRoot().getRightChild().getRightChild().getValue()
        );

        System.out.println("layer 3 : " +
                binaryTree.getRoot().getRightChild().getLeftChild().getLeftChild().getValue());

    }

}
