package com.tomzhu.external;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;


/**
 * testing {@link BPlusTree}
 *
 * @author tomzhu
 * @since 1.7
 */

public class BPlusTreeTest {

    private String path = "/tmp/tmp_test";
    private BPlusTree bPlusTree;
    private HashMap<Integer, Integer> hashMap;
    private int size = 100;

    @Test
    public void addOrUpdate() {
    }

    @Test
    public void get() throws IOException {
        File f = new File(path);
        for (File subFile : f.listFiles()) subFile.delete();
        bPlusTree = new BPlusTree(path);
        hashMap = new HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; ) {
            if (i != 0 && i % 5 == 0) {
                //testing replacing
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(bPlusTree.contains(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                int newValue = rand.nextInt();
                if (newValue == 0) newValue = 1;
                if (newValue < 0) newValue = -newValue;
                hashMap.put(k, newValue);
                bPlusTree.addOrUpdate(k, newValue);
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                //testing removing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(bPlusTree.contains(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.remove(k));
                hashMap.remove(k);
                assertFalse(bPlusTree.contains(k));
                i ++;
            } else {
                int k = rand.nextInt();
                int v = rand.nextInt();
                if (v == 0) v = 1;
                if (v < 0) v = -v;
                if (hashMap.containsKey(k)) {
                    assertTrue(bPlusTree.contains(k));
                    assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                    continue;
                } else {
                    // adding
                    assertFalse(bPlusTree.contains(k));
                    hashMap.put(k, v);
                    bPlusTree.addOrUpdate(k, v);
                    assertTrue(bPlusTree.contains(k));
                    i ++;
                }
            }
        }
        bPlusTree.close();

        bPlusTree = new BPlusTree(path);
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(bPlusTree.contains(entry.getKey()));
            assertEquals((long) entry.getValue(), (long) bPlusTree.get(entry.getKey()));
        }

        for (int i = 0; i < size; ) {
            if (i != 0 && i % 5 == 0) {
                //testing replacing
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(bPlusTree.contains(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                int newValue = rand.nextInt();
                if (newValue == 0) newValue = 1;
                if (newValue < 0) newValue = -newValue;
                hashMap.put(k, newValue);
                bPlusTree.addOrUpdate(k, newValue);
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                //testing removing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(bPlusTree.contains(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                assertEquals((long) hashMap.get(k), (long) bPlusTree.remove(k));
                hashMap.remove(k);
                assertFalse(bPlusTree.contains(k));
                i ++;
            } else {
                int k = rand.nextInt();
                int v = rand.nextInt();
                if (v == 0) v = 1;
                if (v < 0) v = -v;
                if (hashMap.containsKey(k)) {
                    assertTrue(bPlusTree.contains(k));
                    assertEquals((long) hashMap.get(k), (long) bPlusTree.get(k));
                    continue;
                } else {
                    // adding
                    assertFalse(bPlusTree.contains(k));
                    hashMap.put(k, v);
                    bPlusTree.addOrUpdate(k, v);
                    assertTrue(bPlusTree.contains(k));
                    i ++;
                }
            }
        }
    }

    @Test
    public void contains() {
    }
}