package com.tomzhu.tree;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-6-26.
 */
public class TreeSetTest {

    private TreeSet<Integer> treeSet = new TreeSet<Integer>();

    {
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(10);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(treeSet.contains(1));
        assertTrue(treeSet.contains(10));
    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void remove() throws Exception {
        assertTrue(treeSet.contains(3));
        treeSet.remove(3);
        assertFalse(treeSet.contains(3));
    }

}