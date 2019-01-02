package com.tomzhu.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BinarySearchTree}
 */
public class BinarySearchTreeTest {

    int size = 5;
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
         inserting : -1682744412
         inserting : -1975608872
         inserting : -1696605931
         trying : -1975608872
         trying : -1696605931
         */
        tree = new BinarySearchTree();
        int[] arr1 = new int[] {100, 10, 20};
        int[] arr2 = new int[] {10, 20, 100};
        for (int i = 0; i < 3; i++) tree.insert(arr1[i]);

        for (int i = 0; i < 3 ; i++) {
            tree.remove(arr2[i]);
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