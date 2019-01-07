package io.github.xiaobogaga.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link HashMap}
 *
 * @author tomzhu
 * @since 1.7
 */

public class HashMapTest {

    java.util.HashMap<Integer, Integer> hashMap1;
    HashMap<Integer, Integer> hashMap2;
    int size = 1000;
    int initCapacity = 16;

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
                data[indicator] = key;
            } else {
                // adding random key, value
                int key = random.nextInt();
                int value = random.nextInt();
                hashMap1.put(key, value);
                hashMap2.put(key, value);
                data[indicator] = key;
            }
            indicator++;
        }
        //  testing
        assertEquals(hashMap1.size(), hashMap2.getSize());
        for (Map.Entry<Integer, Integer> entry : hashMap1.entrySet()) {
            assertEquals(entry.getValue(), hashMap2.get(entry.getKey()));
            assertTrue(hashMap2.contains(entry.getKey()));
            assertTrue(hashMap2.remove(entry.getKey()));
            assertFalse(hashMap2.contains(entry.getKey()));
            assertNull(hashMap2.get(entry.getKey()));
        }
        assertEquals(0, hashMap2.getSize());
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

        /*
        0 = -1357758087 // here.
        1 = 698393520
        2 = 1435869658
         */


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