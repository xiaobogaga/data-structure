package com.tomzhu.hash;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by IIE on 2018/7/6.
 */
public class ExtensibleHashTableTest {

    private ExtensibleHashTable<Integer, Integer> extensibleHashTable =
            new ExtensibleHashTable<Integer, Integer>();

    private int size = 1000;
    private HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        int t = 0;
        int testTimes = 1000;
        while (t < testTimes) {
            int i = 0;
            System.out.println("test time : " + (t + 1));
            while (i < size) {
                int v = (int) (Math.random() * 1000000);
                if (!maps.containsKey(v)) {
                    extensibleHashTable.insert(v, v);
                    maps.put(v, v);
                    i++;
                }
            }
            for (Integer key : maps.keySet()) {
                assertTrue(extensibleHashTable.contains(key));
            }
            maps.clear();
            extensibleHashTable = new ExtensibleHashTable<Integer, Integer>();
            t ++;
        }
    }

    @Test
    public void remove() throws Exception {

        int t = 0;
        int testTimes = 1000;
        while (t < testTimes) {
            int i = 0;
            System.out.println("test time : " + (t + 1));
            while (i < size) {
                int v = (int) (Math.random() * 1000000);
                if (!maps.containsKey(v)) {
                    extensibleHashTable.insert(v, v);
                    maps.put(v, v);
                    i++;
                }
            }

            for (Integer key : maps.keySet()) {
                assertTrue(extensibleHashTable.remove(key));
                assertFalse(extensibleHashTable.contains(key));
            }

            maps.clear();
            t ++;
        }
    }

}