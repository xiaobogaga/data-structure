package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by IIE on 2018/7/6.
 */
public class ExtensibleHashTableTest {

    private ExtensibleHashTable<Integer, Integer> extensibleHashTable;

    private int size = 1000;
    private HashMap<Integer, Integer> hashMap;

    @Test
    public void insert() throws Exception {
        hashMap = new HashMap<>();
        extensibleHashTable = new ExtensibleHashTable<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(extensibleHashTable.contains(k));
                assertEquals(extensibleHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                extensibleHashTable.insert(k, newV);
                assertTrue(extensibleHashTable.contains(k));
                assertEquals((int) extensibleHashTable.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(extensibleHashTable.contains(k));
                assertEquals(extensibleHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(extensibleHashTable.remove(k));
                assertFalse(extensibleHashTable.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(extensibleHashTable.contains(k));
                    assertEquals(extensibleHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(extensibleHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                extensibleHashTable.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(extensibleHashTable.contains(entry.getKey()));
            assertEquals(extensibleHashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void contains() throws Exception {

    }

    @Test
    public void remove() throws Exception {


    }

}