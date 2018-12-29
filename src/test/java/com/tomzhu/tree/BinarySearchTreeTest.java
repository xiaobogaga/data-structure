package com.tomzhu.tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 17-7-20.
 */
public class BinarySearchTreeTest {

    int size = 100;
    private BinarySearchTree tree;

//    {
//        java.util.TreeMap<Integer, Integer> test = new java.util.TreeMap<>();
//        test.put(1, 1);
//        test.put(2, 2);
//        System.out.println(test.firstKey()); // 1
//        System.out.println(test.lastKey()); // 2
//    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void getMax() throws Exception {
    }

    @Test
    public void getMin() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void remove() throws Exception {
        tree = new BinarySearchTree();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
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
    public void isEmpty() throws Exception {
    }

    @Test
    public void getRoot() throws Exception {
    }

}