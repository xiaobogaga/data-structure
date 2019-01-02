package com.tomzhu.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-18.
 */
public class AVLTreeTest {

    private static AVLTree<Integer> tree;
    int size = 7;

    @Test
    public void contains() throws Exception {
        /**
         * inserting 1159769474
         * inserting 425076822
         * inserting -59947419
         * inserting 836443500
         * inserting 1429722747
         * inserting 681043646
         * inserting -836095107
         * removing : 425076822
         * removing : 1159769474
         * removing : 836443500
         * removing : -836095107
         */

        tree = new AVLTree<>();
        int[] arr1 = new int[] {1159, 425, -599, 836, 1429, 681, -836};
        for (int i = 0; i < size; i++) tree.insert(arr1[i]);
        int[] arr2 = new int[] {425, 1159, 836, -836};
        int size2 = 4;
        for (int i = 0; i < size2; i++) {
            assertTrue(tree.remove(arr2[i]));
            assertFalse(tree.contains(arr2[i]));
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
        tree = new AVLTree<Integer>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
            System.out.println("inserting " + k);
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
            System.out.println("removing : " + entry.getKey());
            assertTrue(tree.remove(entry.getKey()));
            assertFalse(tree.contains(entry.getKey()));
        }
        assertTrue(tree.isEmpty());
    }

    private void printAVLTree(AVLTreeNode node) {
    }


    @Test
    public void remove() throws Exception {

    }

}