package com.tomzhu.tree;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link TreeMap}
 *
 * @author tomzhu
 * @since 1.7
 */
public class TreeMapTest {

    private TreeMap<Integer, Integer> treeMap;
    private int size = 1000;

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void insert() throws Exception {
        treeMap = new TreeMap<>();
        java.util.TreeMap<Integer, Integer> treeMap2 = new java.util.TreeMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(treeMap.isEmpty());
        for (int i = 0; i < size; ) {
            int k = rand.nextInt();
            if (treeMap2.containsKey(k)) continue;
            treeMap2.put(k, k);
            assertFalse(treeMap.contains(k));
            assertTrue(treeMap.insert(k, k));
            assertTrue(treeMap.contains(k));
            if (i != 0 && i % 5 == 0) {
                // testing remove.
                Object[] arr = treeMap2.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k2 = (int) arr[loc % arr.length];
                treeMap2.remove(k2);
                assertTrue(treeMap.contains(k2));
                assertTrue(treeMap.remove(k2));
                assertFalse(treeMap.contains(k2));
            }
            i ++;
        }
        assertFalse(treeMap.isEmpty());
        Object[] arr = treeMap2.keySet().toArray();
        for (Object i : arr) {
            assertEquals(treeMap2.get(i), treeMap.get((int) i));
            assertTrue(treeMap.contains((int) i));
            assertTrue(treeMap.remove((int) i));
            assertFalse(treeMap.contains((int) i));
        }
    }

    @Test
    public void remove() throws Exception {
    }


}