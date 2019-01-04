package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link HashTableBySeparateChain}
 *
 * @author tomzhu
 * @since 1.7
 */

public class HashTableBySeparateChainTest {

    private HashTableBySeparateChain<Integer, Integer> separateChainHashTable;
    private java.util.HashMap<Integer, Integer> hashMap;
    private int size = 1000;

    @Test
    public void insert() throws Exception {
        hashMap = new HashMap<>();
        separateChainHashTable = new HashTableBySeparateChain<>(45, 0.5f);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(separateChainHashTable.contains(k));
                assertEquals(separateChainHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                separateChainHashTable.insert(k, newV);
                assertTrue(separateChainHashTable.contains(k));
                assertEquals((int) separateChainHashTable.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(separateChainHashTable.contains(k));
                assertEquals(separateChainHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(separateChainHashTable.remove(k));
                assertFalse(separateChainHashTable.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(separateChainHashTable.contains(k));
                    assertEquals(separateChainHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(separateChainHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                separateChainHashTable.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(separateChainHashTable.contains(entry.getKey()));
            assertEquals(separateChainHashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @org.junit.Test
    public void remove() throws Exception {

    }

    @org.junit.Test
    public void contains() throws Exception {

    }


}
