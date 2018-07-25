package com.tomzhu.tree.iterator;

import com.tomzhu.tree.BinaryTree;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-21.
 */
public class PreOrderIteratorTest {

    private BinaryTree<Integer> tree = new BinaryTree<Integer>(10);
    {
        tree.getRoot().addLeftChild(4);
        tree.getRoot().addRightChild(8);
        tree.getRoot().getLeftChild().addLeftChild(2);
        tree.getRoot().getLeftChild().addRightChild(6);
        tree.getRoot().getLeftChild().getRightChild().addRightChild(7);
        tree.getRoot().getRightChild().addLeftChild(7);



    }

    private PreOrderIterator<Integer> preOrderIterator = new PreOrderIterator<Integer>(tree.getRoot());

    {
        while (preOrderIterator.hasNext())
            System.out.println(preOrderIterator.next().getValue());
    }

    @Test
    public void hasNext() throws Exception {
       // Assert.assertTrue(preOrderIterator.hasNext());
    }

    @Test
    public void next() throws Exception {
       // Assert.assertEquals(10 , (int) preOrderIterator.next().getValue());
    }

    @Test
    public void remove() throws Exception {
        // pass
    }

}