package com.tomzhu.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BinarySearchTree}
 */
public class BinarySearchTreeTest {

    int size = 1000;
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
        /**
         inserting : -844718477  -844, 2018, -942, 1832, 164
         inserting : 2018823543
         inserting : -942839450
         inserting : 1832127170
         inserting : 164074544
         trying : 2018823543
         trying : -844718477
         trying : 1832127170
         */
        tree = new BinarySearchTree();
        int[] arr1 = new int[] {-844, 2018, -942, 1832, 164};
        int[] arr2 = new int[] {2018, -844, 1832};
        for (int i = 0; i < 5; i++) tree.insert(arr1[i]);

        for (int i = 0; i < 5 ; i++) {
            assertTrue(tree.remove(arr2[i]));
            assertFalse(tree.contains(arr2[i]));
        }
    }

    @Test
    public void remove() throws Exception {
        tree = new BinarySearchTree();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
            // System.out.println("inserting : " + k);
            maps.put(k, k);
            hashMap.put(k, k);
            tree.insert(k);
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            i ++;
        }
        assertFalse(tree.isEmpty());

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) assertTrue(tree.contains(entry.getKey()));

        // testing remove.
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // System.out.println("trying : " + entry.getKey());
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