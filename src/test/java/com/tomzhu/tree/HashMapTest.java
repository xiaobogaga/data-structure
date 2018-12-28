package com.tomzhu.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * a simple test for hashMap.
 */

public class HashMapTest {

    java.util.HashMap<Integer, Integer> hashMap1;
    HashMap<Integer, Integer> hashMap2;
    int size = 3;
    int initCapacity = 1;

    @Test
    public void remove() {
        hashMap1 = new java.util.HashMap<Integer, Integer>();
        hashMap2 = new HashMap<Integer, Integer>(initCapacity);
        Random random = new Random(System.currentTimeMillis());
        int[] data = new int[size];
        int indicator = 0;
        for (int i = 0; i < size; i++) {
            if (i != 0 && i % 3 == 0) {
                // adding duplicate items.
                int loc = random.nextInt();
                if (loc < 0) loc = -loc;
                loc = loc % indicator;
                int key = data[loc];
                int value = random.nextInt();
                hashMap1.put(key, value);
                hashMap2.put(key, value);
            } else {
                // adding random key, value
                int key = random.nextInt();
                int value = random.nextInt();
                hashMap1.put(key, value);
                hashMap2.put(key, value);
            }
            indicator = hashMap1.size();
        }
        //  testing
        assertEquals(hashMap1.size(), hashMap2.getSize());
        for (Map.Entry<Integer, Integer> entry : hashMap1.entrySet()) {
            assertTrue(hashMap2.contains(entry.getKey()));
            hashMap2.remove(entry.getKey());
            assertFalse(hashMap2.contains(entry.getKey()));
            assertNull(hashMap2.get(entry.getKey()));
        }


    }

    @Test
    public void put() {
        hashMap1 = new java.util.HashMap<Integer, Integer>();
        hashMap2 = new HashMap<Integer, Integer>(initCapacity);
        Random random = new Random(System.currentTimeMillis());
        int[] data = new int[size];
        int indicator = 0;
        for (int i = 0; i < size; i++) {
            if (i != 0 && i % 3 == 0) {
                // adding duplicate items.
                int loc = random.nextInt();
                if (loc < 0) loc = -loc;
                loc = loc % indicator;
                int key = data[loc];
                int value = random.nextInt();
                hashMap1.put(key, value);
                hashMap2.put(key, value);
                data[indicator] = key;
            } else {
                // adding random key, value
                int key = random.nextInt();
                int value = random.nextInt();
                hashMap1.put(key, value);
                hashMap2.put(key, value);
                data[indicator] = key;
            }
            indicator ++;
        }
        //  testing
        assertEquals(hashMap1.size(), hashMap2.getSize());
        for (Map.Entry<Integer, Integer> entry : hashMap1.entrySet()) {
            assertTrue(hashMap2.contains(entry.getKey()));
            assertEquals(hashMap2.get(entry.getKey()), entry.getValue());
        }

    }

    @Test
    public void contains() { }

    @Test
    public void getSize() { }

    @Test
    public void getLoadFactor() { }

    @Test
    public void setLoadFactor() { }
}