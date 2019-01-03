package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 */
public class HopscotchHashTableTest {

    private HopscotchHashTable<Integer, Integer> hopscotchHashTable;
    private int size = 100;
    private java.util.HashMap<Integer, Integer> hashMap;



    @Test
    public void insert() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        hashMap = new HashMap<>();
        hopscotchHashTable = new HopscotchHashTable<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                hopscotchHashTable.insert(k, newV);
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals((int) hopscotchHashTable.get(k), newV);
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(hopscotchHashTable.remove(k));
                assertFalse(hopscotchHashTable.contains(k));
                i ++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(hopscotchHashTable.contains(k));
                    assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(hopscotchHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                hopscotchHashTable.insert(k, v);
                i ++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(hopscotchHashTable.contains(entry.getKey()));
            assertEquals(hopscotchHashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void remove() throws Exception {

    }

}