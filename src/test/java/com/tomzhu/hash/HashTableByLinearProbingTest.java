package com.tomzhu.hash;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 2018/6/29.
 */
public class HashTableByLinearProbingTest {

    private HashTableByLinearProbing<Integer, Integer> probingHashTable =
            new HashTableByLinearProbing<Integer, Integer>(45, 0.5f);
    private int[] arrs;
    {
        int i = 0;
        int size = 1000;
        this.arrs = new int[size];
        while (i < size) {
            arrs[i] = (int) (Math.random() * Integer.MAX_VALUE);
            probingHashTable.insert(arrs[i], arrs[i]);
            i ++;
        }
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        for (int i : arrs) {
            assertTrue(this.probingHashTable.contains(i));
        }
    }

    @Test
    public void remove() throws Exception {
        for (int i : arrs) {
            this.probingHashTable.remove(i);
            assertFalse(this.probingHashTable.contains(i));
        }
    }

}