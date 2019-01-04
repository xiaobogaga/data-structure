package com.tomzhu.tree;

import com.tomzhu.tree.SkipList;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link SkipList}
 *
 * @author tomzhu.
 * @since 1.7
 */
public class SkipListTest {

    private SkipList<Integer> skipList;
    private int size;
    private int[] arr;
    private HashMap<Integer, Integer> map;


    @Test
    public void insert() throws Exception {
        size = 1000;
        skipList = new SkipList<Integer>(32);
        arr = new int[size];
        map = new HashMap<Integer, Integer>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(skipList.isEmpty());
        int i = 0, t = 0;
        while (i < size) {
            t = (int) (rand.nextDouble() * 100000);
            if (!map.containsKey(t)) {
                arr[i] = t;
                map.put(t, t);
                skipList.insert(t);
                i ++;
            }
        }
        assertFalse(skipList.isEmpty());
        for (int z : arr) {
            assertTrue(skipList.contains(z));
            assertTrue(skipList.remove(z));
        }
        assertTrue(skipList.isEmpty());
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void isEmpty() throws Exception {
    }

}