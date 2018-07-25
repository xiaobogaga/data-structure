package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class CuckooHashTableTest {

    private CuckooHashTable<Integer, Integer> cuckooHashTable =
            new CuckooHashTable<Integer, Integer>(17, CuckooHashTable.HashTableType.FOUR);
    private HashMap<Integer, Integer> arr = new HashMap<Integer, Integer>();
    private Random rand = new Random(System.currentTimeMillis());
    private int size = 1000;
    {
        int i = 0;
        while (i < size) {
            int k = rand.nextInt(10000000);
            if (!arr.containsKey(k)) {
                arr.put(k, k);
                cuckooHashTable.insert(k, k);
            }
            i ++;
        }
    }

    @Test
    public void contains() {
        for (int t : arr.keySet()) {
            assertTrue(cuckooHashTable.contains(t));
        }
    }

    @Test
    public void remove() {
        for (int t : arr.keySet()) {
            assertTrue(cuckooHashTable.remove(t));
            assertFalse(cuckooHashTable.contains(t));
        }
    }
}