package com.tomzhu.tree;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link TreeSet}
 *
 * @author tomzhu
 * @since 1.7
 */
public class TreeSetTest {

    private TreeSet<Integer> treeSet;
    private int size = 1000;

    @Test
    public void contains() throws Exception {
        treeSet = new TreeSet<>();
        java.util.TreeSet<Integer> treeSet2 = new java.util.TreeSet<>();
        Random rand = new Random();
        assertTrue(treeSet.isEmpty());
        for (int i = 0; i < size; ) {
            int k = rand.nextInt();
            if (treeSet2.contains(k)) continue;
            treeSet2.add(k);
            assertFalse(treeSet.contains(k));
            assertTrue(treeSet.add(k));
            assertTrue(treeSet.contains(k));
            if (i != 0 && i % 5 == 0) {
                // testing remove.
                Object[] arr = treeSet2.toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k2 = (int) arr[loc % arr.length];
                treeSet2.remove(k2);
                assertTrue(treeSet.remove(k2));
                assertFalse(treeSet.contains(k2));
            }
            i ++;
        }
        assertFalse(treeSet.isEmpty());
        Object[] arr = treeSet2.toArray();
        for (Object i : arr) {
            assertTrue(treeSet.contains((int) i));
            assertTrue(treeSet.remove((int) i));
            assertFalse(treeSet.contains((int) i));
        }
    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void remove() throws Exception {
    }

}