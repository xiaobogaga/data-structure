package com.tomzhu.tree;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * {@link BinaryTree}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BinaryTreeTest {

    private BinaryTree<Integer> binaryTree = new BinaryTree<Integer>(10);

    @org.junit.Test
    public void isEmpty() throws Exception {
        Assert.assertFalse(binaryTree.isEmpty());
    }

    @org.junit.Test
    public void getRoot() throws Exception {
        Assert.assertEquals(10 , (int) binaryTree.getRoot().getValue());
    }

    @org.junit.Test
    public void hasLeftChild() throws Exception {
        Assert.assertFalse(binaryTree.hasLeftChild(binaryTree.getRoot()));
        binaryTree.addLeftChild(binaryTree.getRoot() , 4);
        Assert.assertTrue(binaryTree.hasLeftChild(binaryTree.getRoot()));
    }

    @org.junit.Test
    public void hasRightChild() throws Exception {
        Assert.assertFalse(binaryTree.hasRightChild(binaryTree.getRoot()));
        binaryTree.addRightChild(binaryTree.getRoot() , 8);
        Assert.assertTrue(binaryTree.hasRightChild(binaryTree.getRoot()));
    }

    @org.junit.Test
    public void isRoot() throws Exception {
        Assert.assertTrue(binaryTree.isRoot(binaryTree.getRoot()));
    }

    @org.junit.Test
    public void addLeftChild() throws Exception {
        binaryTree.addLeftChild(binaryTree.getRoot() , 4);
        Assert.assertEquals(4 , (int) binaryTree.getRoot().getLeftChild().getValue());
    }

    @org.junit.Test
    public void addRightChild() throws Exception {
        binaryTree.addRightChild(binaryTree.getRoot() , 8);
        Assert.assertEquals(8 , (int) binaryTree.getRoot().getRightChild().getValue());
    }

    @org.junit.Test
    public void addLeftChild1() throws Exception {
        binaryTree.addLeftChild(binaryTree.getRoot() , new BinaryTreeNode<Integer>(4));
        Assert.assertEquals(4 , (int) binaryTree.getRoot().getLeftChild().getValue());
    }

    @org.junit.Test
    public void addRightChild1() throws Exception {
        binaryTree.addRightChild(binaryTree.getRoot() , new BinaryTreeNode<Integer>(8));
        Assert.assertEquals(8 , (int) binaryTree.getRoot().getRightChild().getValue());
    }

    @org.junit.Test
    public void getLeftChild() throws Exception {
        binaryTree.addLeftChild(binaryTree.getRoot() , 4);
        Assert.assertEquals(4 , (int) binaryTree.getRoot().getLeftChild().getValue());
    }

    @org.junit.Test
    public void getRightChild() throws Exception {
        binaryTree.addRightChild(binaryTree.getRoot() , 8);
        Assert.assertEquals(8 , (int) binaryTree.getRoot().getRightChild().getValue());
    }

    @org.junit.Test
    public void clearChildren() throws Exception {
        binaryTree.getRoot().addRightChild(8);
        binaryTree.getRoot().addLeftChild(4);
        Assert.assertTrue(binaryTree.getRoot().hasChildren());
        binaryTree.clearChildren(binaryTree.getRoot());
        Assert.assertFalse(binaryTree.getRoot().hasChildren());
    }

    @org.junit.Test
    public void removeLeftChild() throws Exception {
        binaryTree.getRoot().addRightChild(8);
        binaryTree.getRoot().addLeftChild(4);
        Assert.assertEquals(4 , (int) binaryTree.getRoot().getLeftChild().getValue());
        Assert.assertEquals(4 , (int) binaryTree.removeLeftChild(binaryTree.getRoot()).getValue());
        Assert.assertFalse(binaryTree.getRoot().hasLeftChild());
    }

    @org.junit.Test
    public void removeRightChild() throws Exception {
        binaryTree.getRoot().addRightChild(8);
        binaryTree.getRoot().addLeftChild(4);
        Assert.assertEquals(8 , (int) binaryTree.getRoot().getRightChild().getValue());
        Assert.assertEquals(8 , (int) binaryTree.removeRightChild(binaryTree.getRoot()).getValue());
        Assert.assertFalse(binaryTree.getRoot().hasRightChild());
    }

}