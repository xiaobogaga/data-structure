package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link CuckooHashTable}
 *
 * @author tomzhu
 * @since 1.7
 */
public class CuckooHashTableTest {

    private CuckooHashTable<Integer, Integer> cuckooHashTable;
    private java.util.HashMap<Integer, Integer> hashMap;
    private int size = 100;

    @Test
    public void contains() {
        hashMap = new HashMap<>();
        cuckooHashTable =  new CuckooHashTable<Integer, Integer>(17, CuckooHashTable.HashTableType.FOUR);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(cuckooHashTable.contains(k));
                assertEquals(cuckooHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                cuckooHashTable.insert(k, newV);
                assertTrue(cuckooHashTable.contains(k));
                assertEquals((int) cuckooHashTable.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(cuckooHashTable.contains(k));
                assertEquals(cuckooHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(cuckooHashTable.remove(k));
                assertFalse(cuckooHashTable.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(cuckooHashTable.contains(k));
                    assertEquals(cuckooHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(cuckooHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                cuckooHashTable.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(cuckooHashTable.contains(entry.getKey()));
            assertEquals(cuckooHashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void remove() {
    }
}