package com.tomzhu.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-19.
 */
public class SplayTreeTest {

    private SplayTree<Integer> tree;
    int size = 3;

//    {
//        tree = new SplayTree<>();
//        tree.insert(214755004);
//        tree.insert(-968702223);
//        tree.insert(-619472113);
//    }

    @Test
    public void getRoot() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        tree = new SplayTree<>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
           // System.out.println("inserting " + k);
            maps.put(k, k);
            tree.insert(k);
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            i ++;
        }
        assertFalse(tree.isEmpty());

        for (Map.Entry<Integer, Integer> entry : maps.entrySet()) assertTrue(tree.contains(entry.getKey()));

        // testing remove.
        for (Map.Entry<Integer, Integer> entry : maps.entrySet()) {
            assertTrue(tree.remove(entry.getKey()));
            assertFalse(tree.contains(entry.getKey()));
        }
        assertTrue(tree.isEmpty());
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
    }

}