package com.tomzhu.tree.iterator;

import com.tomzhu.tree.BinaryTree;
import com.tomzhu.tree.BinaryTreeNode;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-28.
 */
public class PostOrderIteratorTest {

    private BinaryTree<Integer> binaryTree = new BinaryTree(10);
    {
        binaryTree.addLeftChild(binaryTree.getRoot(), 4).addLeftChild(3);
        binaryTree.getLeftChild(binaryTree.getRoot()).addRightChild(5);
        binaryTree.addRightChild(binaryTree.getRoot() , 8).addLeftChild(6).addLeftChild(5);
        binaryTree.getRightChild(binaryTree.getRoot()).addRightChild(9);
    }



    @Test
    public void hasNext() throws Exception {
        PreOrderIterator<Integer> preIterator = new PreOrderIterator<Integer>(binaryTree.getRoot());
        while (preIterator.hasNext())
            System.out.println(preIterator.next().getValue());
        System.out.println("------------------------");
        PostOrderIterator<Integer> iterator = new PostOrderIterator<Integer>(binaryTree.getRoot());
        while (iterator.hasNext())
            System.out.println(iterator.next().getValue());
    }

    @Test
    public void next() throws Exception {
    }

}