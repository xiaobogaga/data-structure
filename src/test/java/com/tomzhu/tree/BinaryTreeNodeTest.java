package com.tomzhu.tree;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-18.
 */
public class BinaryTreeNodeTest {

    private BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(10);

    {
        root.addLeftChild(4);
        root.addRightChild(8);
    }

    @org.junit.Test
    public void hasLeftSibling() throws Exception {
        Assert.assertFalse(root.hasLeftSibling());
        Assert.assertTrue(root.getRightChild().hasLeftSibling());
    }

    @org.junit.Test
    public void hasRightSibling() throws Exception {
        Assert.assertFalse(root.hasRightSibling());
        Assert.assertTrue(root.getLeftChild().hasRightSibling());
    }

    @org.junit.Test
    public void hasLeftChild() throws Exception {
        Assert.assertTrue(root.hasLeftChild());
    }

    @org.junit.Test
    public void hasRightChild() throws Exception {
        Assert.assertTrue(root.hasRightChild());
    }

    @org.junit.Test
    public void isRoot() throws Exception {
        Assert.assertTrue(root.isRoot());
        Assert.assertFalse(root.getLeftChild().isRoot());
    }

    @org.junit.Test
    public void addLeftChild() throws Exception {
        Assert.assertEquals(4 , (int) root.getLeftChild().getValue());
    }

    @org.junit.Test
    public void addRightChild() throws Exception {
        Assert.assertEquals(8 , (int) root.getRightChild().getValue());
    }

    @org.junit.Test
    public void addLeftChild1() throws Exception {
        root.getLeftChild().addLeftChild(new BinaryTreeNode<Integer>(2));
        Assert.assertEquals(2 , (int) root.getLeftChild().getLeftChild().getValue());
    }

    @org.junit.Test
    public void addRightChild1() throws Exception {
        root.getLeftChild().addRightChild(new BinaryTreeNode<Integer>(6));
        Assert.assertEquals(6 , (int) root.getLeftChild().getRightChild().getValue());
    }

    @org.junit.Test
    public void getLeftChild() throws Exception {
        Assert.assertEquals(4 , (int) root.getLeftChild().getValue());
    }

    @org.junit.Test
    public void getRightChild() throws Exception {
        Assert.assertEquals(8 , (int) root.getRightChild().getValue());
    }

    @org.junit.Test
    public void clearChildren() throws Exception {
        root.clearChildren();
        Assert.assertFalse(root.hasChildren());
        Assert.assertNull(root.getLeftChild());
        Assert.assertNull(root.getRightChild());
    }

    @org.junit.Test
    public void removeLeftChild() throws Exception {
        Assert.assertEquals(4 , (int) root.getLeftChild().getValue());
        root.removeLeftChild();
        Assert.assertNull(root.getLeftChild());
    }

    @org.junit.Test
    public void removeRightChild() throws Exception {
        Assert.assertEquals(8 , (int) root.getRightChild().getValue());
        root.removeRightChild();
        Assert.assertNull(root.getRightChild());
    }

}