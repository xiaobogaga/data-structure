package com.tomzhu.tree;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-19.
 */
public class SplayTreeTest {

    private static SplayTree<Integer> tree;

    static {
        tree = new SplayTree<Integer>();
        int time = 1, i = 7;
        while (i >= time) {
            tree.insert(i);
            i--;
        }
    }

    @Test
    public void getRoot() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        printSplayTree(tree.getRootNode());
        int time = 1;
        while (time <= 7) {
            tree.contains(time);
            System.out.println("testing -- > " + time);
            printSplayTree(tree.getRootNode());
            time ++;
        }
    }

    @Test
    public void findMax() throws Exception {
    }

    @Test
    public void findMin() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

    private void printSplayTree(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        printSplayTree(node.getLeftChild());
        printSplayTree(node.getRightChild());
    }

}