package com.tomzhu.hash;

import org.junit.Test;

import java.lang.annotation.Repeatable;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 */
public class HopscotchHashTableTest {

    private HopscotchHashTable<Integer, Integer> hopscotchHashTable =
            new HopscotchHashTable<Integer, Integer>();

    private int size = 20000;
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    {
        int i = 0;
        int t = 0;
        while (i < size) {
            t = (int) (Math.random() * 1000000);
            if (!map.containsKey(t)) {
                map.put(t, t);
                hopscotchHashTable.insert(t, t);
            }
            i ++;
        }
    }


    @Test
    public void insert() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        for (int t : this.map.keySet())
            assertTrue(this.hopscotchHashTable.contains(t));
    }

    @Test
    public void remove() throws Exception {
        for (int t : this.map.keySet()) {
            assertTrue(this.hopscotchHashTable.remove(t));
            assertFalse(this.hopscotchHashTable.contains(t));
        }
    }

}