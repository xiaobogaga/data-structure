package io.github.xiaobogaga.hash;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link HashTableByLinearProbing}
 *
 * @author tomzhu
 * @since 1.7
 */
public class HashTableByLinearProbingTest {

    private HashTableByLinearProbing<Integer, Integer> probingHashTable;

    private int size = 1000;

    private java.util.HashMap<Integer, Integer> hashMap;

    @Test
    public void insert() throws Exception {
        hashMap = new java.util.HashMap<>();
        probingHashTable = new HashTableByLinearProbing<Integer, Integer>(45, 0.5f);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(probingHashTable.contains(k));
                assertEquals(probingHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                probingHashTable.insert(k, newV);
                assertTrue(probingHashTable.contains(k));
                assertEquals((int) probingHashTable.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(probingHashTable.contains(k));
                assertEquals(probingHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(probingHashTable.remove(k));
                assertFalse(probingHashTable.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(probingHashTable.contains(k));
                    assertEquals(probingHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(probingHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                probingHashTable.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(probingHashTable.contains(entry.getKey()));
            assertEquals(probingHashTable.get(entry.getKey()), entry.getValue());
        }

    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}