package com.tomzhu.tree.iterator;

import com.tomzhu.tree.BinaryTree;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-21.
 */
public class LayerIteratorTest {
    private BinaryTree<Integer> tree = new BinaryTree<Integer>(10);
    {
        tree.getRoot().addLeftChild(4);
        tree.getRoot().addRightChild(8);
        tree.getRoot().getLeftChild().addLeftChild(2);
        tree.getRoot().getRightChild().addRightChild(9);

        System.out.println("root : " + tree.getRoot().getValue());
        System.out.println("layer 1 : " + tree.getRoot().getLeftChild().getValue()
                + " , " + tree.getRoot().getRightChild().getValue()
        );

        System.out.println("layer 2 : " + tree.getRoot().getLeftChild().getLeftChild().getValue()
            + " , | , " + tree.getRoot().getRightChild().getRightChild().getValue()
        );

    }

    private LayerIterator<Integer> layerIterator = new LayerIterator<Integer>(tree.getRoot());

    {
        while (layerIterator.hasNext())
            System.out.println(layerIterator.next().getValue());
    }
    @Test
    public void hasNext() throws Exception {
        // pass
    }

    @Test
    public void next() throws Exception {
        // pass
    }

    @Test
    public void remove() throws Exception {
        // pass
    }

}