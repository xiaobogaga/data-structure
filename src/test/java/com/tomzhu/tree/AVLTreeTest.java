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
    int size = 1000;

    @Test
    public void contains() throws Exception {
        /**
         inserting -1819809584 -1819, 1979, 1548, -463, 1029, 491, 220, 1128, 1462, -1086, 1191, 825, -1075, -64, -2027
         inserting 1979286919
         inserting 1548905930
         inserting -463593194
         inserting 1029555040
         inserting 491017514
         inserting 220029634
         inserting 1128919825
         inserting 1462327558
         inserting -1086744216
         inserting 1191628043
         inserting 825925168
         inserting -1075980160
         inserting -64420876
         inserting -2027568271
         removing : -463593194
         removing : 825925168
         */

        tree = new AVLTree<>();
        int[] arr1 = new int[] {-1819, 1979, 1548, -463, 1029, 491, 220, 1128,
                1462, -1086, 1191, 825, -1075, -64, -2027};
        for (int i = 0; i < size; i++)
            tree.insert(arr1[i]);
        int[] arr2 = new int[] {-463, 825};
        int size2 = 5;
        for (int i = 0; i < size2; i++) {
            System.out.println("removing " + arr2[i]);
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
            // System.out.println("inserting " + k);
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
            // System.out.println("removing : " + entry.getKey());
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