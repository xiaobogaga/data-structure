package com.tomzhu.tree;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-6-26.
 */
public class TreeMapTest {

    private TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();

    {
        treeMap.insert(1, 1);
        treeMap.insert(2, 2);
        treeMap.insert(10, 10);
        treeMap.insert(5, 5);
        treeMap.insert(3, 3);
        treeMap.insert(8, 8);
    }


    @Test
    public void contains() throws Exception {
        assertTrue(treeMap.contains(3));
        assertTrue(treeMap.contains(8));
        assertTrue(treeMap.contains(1));
        assertFalse(treeMap.contains(9));
    }

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void remove() throws Exception {
        treeMap.insert(100, 100);
        assertTrue(treeMap.contains(100));
        treeMap.remove(10);
        assertFalse(treeMap.contains(10));
    }


}