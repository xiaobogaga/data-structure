package io.github.xiaobogaga.external;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link HashTable}
 *
 * @author tomzhu
 * @since 1.7
 */
public class HashTableTest {

    private String path = "/tmp/tmp_test";
    private HashTable hashTable;
    private HashMap<Integer, Integer> hashMap;
    private int size = 100;

    @Test
    public void addOrUpdate() throws IOException {
        File f = new File(path);
        for (File subFile : f.listFiles()) subFile.delete();
        hashTable = new HashTable(path, 1000);
        hashMap = new HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; ) {
            if (i != 0 && i % 5 == 0) {
                //testing replacing
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hashTable.contains(k));
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                int newValue = rand.nextInt();
                if (newValue == 0) newValue = 1;
                if (newValue < 0) newValue = -newValue;
                // System.out.println("inserting : " + k + " , " + newValue);
                hashMap.put(k, newValue);
                hashTable.addOrUpdate(k, newValue);
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                //testing removing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                // System.out.println("removing : " + k);
                assertTrue(hashTable.contains(k));
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                assertEquals((long) hashMap.get(k), hashTable.remove(k));
                hashMap.remove(k);
                assertFalse(hashTable.contains(k));
                i ++;
            } else {
                int k = rand.nextInt();
                if (k == 0) k = 1;
                if (k < 0) k = -k;
                int v = rand.nextInt();
                if (v == 0) v = 1;
                if (v < 0) v = -v;
                if (hashMap.containsKey(k)) {
                    assertTrue(hashTable.contains(k));
                    assertEquals((long) hashMap.get(k), hashTable.get(k));
                    continue;
                } else {
                    // adding
                    // System.out.println("inserting : " + k + " , " + v);
                    assertFalse(hashTable.contains(k));
                    hashMap.put(k, v);
                    hashTable.addOrUpdate(k, v);
                    assertTrue(hashTable.contains(k));
                    i ++;
                }
            }
        }
        hashTable.close();

        hashTable = new HashTable(path, 1000);
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(hashTable.contains(entry.getKey()));
            assertEquals((long) entry.getValue(), hashTable.get(entry.getKey()));
        }

        for (int i = 0; i < size; ) {
            if (i != 0 && i % 5 == 0) {
                //testing replacing
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hashTable.contains(k));
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                int newValue = rand.nextInt();
                if (newValue == 0) newValue = 1;
                if (newValue < 0) newValue = -newValue;
                hashMap.put(k, newValue);
                hashTable.addOrUpdate(k, newValue);
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                i ++;
            } else if (i != 0 && i % 4 == 0) {
                //testing removing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hashTable.contains(k));
                assertEquals((long) hashMap.get(k), hashTable.get(k));
                assertEquals((long) hashMap.get(k), hashTable.remove(k));
                hashMap.remove(k);
                assertFalse(hashTable.contains(k));
                i ++;
            } else {
                int k = rand.nextInt();
                if (k == 0) k = 1;
                if (k < 0) k = -k;
                int v = rand.nextInt();
                if (v == 0) v = 1;
                if (v < 0) v = -v;
                if (hashMap.containsKey(k)) {
                    assertTrue(hashTable.contains(k));
                    assertEquals((long) hashMap.get(k), hashTable.get(k));
                    continue;
                } else {
                    // adding
                    assertFalse(hashTable.contains(k));
                    hashMap.put(k, v);
                    hashTable.addOrUpdate(k, v);
                    assertTrue(hashTable.contains(k));
                    i ++;
                }
            }
        }

    }

    @Test
    public void get() throws IOException {

    }

    @Test
    public void contains() {
    }
}