package com.tomzhu.hash;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 2018/6/27.
 */

public class HashTableBySeperateChainTest {

    private HashTableBySeperateChain<Integer, Integer> hashTables =
            new HashTableBySeperateChain<Integer, Integer>(54, 0.75f);
    private int[] arrs;

    {

        int i = 0;
        int size = 1000;
        this.arrs = new int[size];
        while (i < size) {
            arrs[i] = (int) (Math.random() * Integer.MAX_VALUE);
            hashTables.insert(arrs[i], arrs[i]);
            i ++;
        }

    }

    @org.junit.Test
    public void insert() throws Exception {

    }

    @org.junit.Test
    public void remove() throws Exception {
        for (int i : arrs) {
            this.hashTables.remove(i);
            assertFalse(this.hashTables.contains(i));
        }
    }

    @org.junit.Test
    public void contains() throws Exception {
        for (int i : arrs) {
            assertTrue(hashTables.contains(i));
        }
    }


}
