package com.tomzhu.hash;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IIE on 2018/6/29.
 */
public class HashTableByQuadraticProbingTest {

    private HashTableByQuadraticProbing<Integer, Integer> hashTableByQuadraticProbing =
            new HashTableByQuadraticProbing<Integer, Integer>();
    private int[] arrs;
    {
        int i = 0;
        int size = 1000;
        this.arrs = new int[size];
        while (i < size) {
            arrs[i] = (int) (Math.random() * Integer.MAX_VALUE);
            hashTableByQuadraticProbing.insert(arrs[i], arrs[i]);
            i ++;
        }
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        for (int i : arrs) {
            assertTrue(this.hashTableByQuadraticProbing.contains(i));
        }
    }

    @Test
    public void remove() throws Exception {
        for (int i : arrs) {
            this.hashTableByQuadraticProbing.remove(i);
            assertFalse(this.hashTableByQuadraticProbing.contains(i));
        }
    }

}