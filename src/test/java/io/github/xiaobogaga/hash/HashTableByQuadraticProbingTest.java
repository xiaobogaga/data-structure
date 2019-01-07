package io.github.xiaobogaga.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * tesing {@link HashTableByQuadraticProbing}
 *
 * @author tomzhu
 * @since 1.7
 */
public class HashTableByQuadraticProbingTest {

    private HashTableByQuadraticProbing<Integer, Integer> hashTableByQuadraticProbing;
    private java.util.HashMap<Integer, Integer> hashMap;
    private int size = 100;

    @Test
    public void insert() throws Exception {
        hashMap = new HashMap<>();
        hashTableByQuadraticProbing = new HashTableByQuadraticProbing<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hashTableByQuadraticProbing.contains(k));
                assertEquals(hashTableByQuadraticProbing.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                hashTableByQuadraticProbing.insert(k, newV);
                assertTrue(hashTableByQuadraticProbing.contains(k));
                assertEquals((int) hashTableByQuadraticProbing.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hashTableByQuadraticProbing.contains(k));
                assertEquals(hashTableByQuadraticProbing.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(hashTableByQuadraticProbing.remove(k));
                assertFalse(hashTableByQuadraticProbing.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(hashTableByQuadraticProbing.contains(k));
                    assertEquals(hashTableByQuadraticProbing.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(hashTableByQuadraticProbing.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                hashTableByQuadraticProbing.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(hashTableByQuadraticProbing.contains(entry.getKey()));
            assertEquals(hashTableByQuadraticProbing.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}