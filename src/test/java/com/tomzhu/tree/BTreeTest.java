package com.tomzhu.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-25.
 */
public class BTreeTest {

    private BTree<Integer> tree = new BTree<Integer>();
    private int size = 5;

    @Test
    public void contains() throws Exception {

        /**
         * -1780816805
         * -1731509307
         * 744054917
         * -1290271866
         * -2068017508
         */
        int[] arr1 = new int[] {-1780816805, -1731509307, 744054917, -1290271866, -2068017508};
        tree = new BTree<>();
        for (int i = 0; i < size; i++) {
            tree.PrintTree();
            tree.insert(arr1[i]);
            System.out.println();
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
        tree = new BTree<>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
            System.out.println();
            System.out.println("inserting : " + k);
            System.out.println();

            maps.put(k, k);
            hashMap.put(k, k);
            tree.insert(k);
            tree.PrintTree();
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            if (i != 0 && i % 5 == 0) {
                Object[] arr = hashMap.values().toArray();
                int l = rand.nextInt();
                if (l < 0) l = -l;
                int removedK = (Integer) arr[l % arr.length];

                System.out.println();
                System.out.println("removing : " + removedK);
                System.out.println();

                hashMap.remove(removedK);
                maps.remove(removedK);
                assertTrue(tree.contains(removedK));
                tree.remove(removedK);
                tree.PrintTree();
                assertFalse(tree.contains(removedK));
                assertEquals(tree.getMin(), maps.firstKey());
                assertEquals(tree.getMax(), maps.lastKey());
            }
            i ++;
        }
        assertFalse(tree.isEmpty());

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println("searching : " + entry.getKey());
            assertTrue(tree.contains(entry.getKey()));
        }

        // testing remove.
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println("removing : " + entry.getKey());
            assertTrue(tree.remove(entry.getKey()));
            assertFalse(tree.contains(entry.getKey()));
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    public void remove() throws Exception {

    }

}