package com.tomzhu.tree;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-20.
 */
public class BinarySearchTreeTest {

    private BinarySearchTree tree = new BinarySearchTree(10);
    {
        tree.insert(8);
        // tree.insert(4);
        tree.insert(9);
        tree.insert(11);

       // System.out.println("root : " + tree.getRoot().getValue());
       // System.out.println("layer 1 : " + tree.getRoot().getLeftChild().getValue() + " , "
       //        );
       // System.out.println("layer 2 : " + tree.getRoot().getLeftChild().getLeftChild().getValue()
       //         + " , " + tree.getRoot().getLeftChild().getRightChild().getValue());
    }

    @Test
    public void contains() throws Exception {
        Assert.assertFalse(tree.contains(20));
        Assert.assertTrue(tree.contains(4));
    }

    @Test
    public void getMax() throws Exception {
        Assert.assertEquals(11 , tree.getMax());
    }

    @Test
    public void getMin() throws Exception {
        Assert.assertEquals(4 , tree.getMin());
    }

    @Test
    public void insert() throws Exception {
        // pass
    }

    @Test
    public void remove() throws Exception {
        tree.remove(8); // remove root node
        Assert.assertFalse(tree.contains(8));
        Assert.assertEquals(10 , tree.getRoot().getValue());
        Assert.assertEquals(11 , tree.getRoot().getRightChild().getValue());
        Assert.assertNull(tree.getRoot().getLeftChild().getLeftChild());
        Assert.assertNull(tree.getRoot().getLeftChild().getRightChild());
        Assert.assertEquals(9 , tree.getRoot().getLeftChild().getValue());
        // Assert.assertEquals(4 , tree.getRoot().getLeftChild().getLeftChild().getValue());
        // Assert.assertTrue(tree.isEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        Assert.assertFalse(tree.isEmpty());
    }

    @Test
    public void getRoot() throws Exception {
        Assert.assertEquals(10 , tree.getRoot().getValue());
    }

}