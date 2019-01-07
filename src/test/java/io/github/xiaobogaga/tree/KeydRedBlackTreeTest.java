package io.github.xiaobogaga.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link KeydRedBlackTree}
 *
 * @author tomzhu
 * @since 1.7
 */

public class KeydRedBlackTreeTest {

    int size = 1000;
    java.util.HashMap<Integer, Integer> maps1;
    KeydRedBlackTree<Integer, Integer> maps2;

    @Test
    public void containsKey() {

    }

    @Test
    public void removeByKey() {
        maps1 = new java.util.HashMap<Integer, Integer>();
        maps2 = new KeydRedBlackTree<Integer, Integer>();
        Random rand = new Random(System.currentTimeMillis());
        int indicator = 0;
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            if (i != 0 && i % 3 == 0) {
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int key = data[loc % indicator];
                int value = rand.nextInt();
                maps1.put(key, value);
                maps2.insert(key, value, 0);
                data[indicator] = key;
            } else {
                int key = rand.nextInt();
                int value = rand.nextInt();
                maps1.put(key, value);
                maps2.insert(key, value, 0);
                data[indicator] = key;
            }
            indicator ++;
        }
        assertEquals(maps1.size(), maps2.size);
        for (Map.Entry<Integer, Integer> entry : maps1.entrySet()) {
            assertTrue(maps2.containsKey(entry.getKey()));
            maps2.removeByKey(entry.getKey());
            assertFalse(maps2.containsKey(entry.getKey()));
            assertNull(maps2.get(entry.getKey()));
        }
    }

    @Test
    public void insert() {
        maps1 = new java.util.HashMap<Integer, Integer>();
        maps2 = new KeydRedBlackTree<Integer, Integer>();
        Random rand = new Random(System.currentTimeMillis());
        int indicator = 0;
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            if (i != 0 && i % 3 == 0) {
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int key = data[loc % indicator];
                int value = rand.nextInt();
                maps1.put(key, value);
                maps2.insert(key, value, 0);
                data[indicator] = key;
            } else {
                int key = rand.nextInt();
                int value = rand.nextInt();
                maps1.put(key, value);
                maps2.insert(key, value, 0);
                data[indicator] = key;
            }
            indicator ++;
        }
        assertEquals(maps1.size(), maps2.size);
        for (Map.Entry<Integer, Integer> entry : maps1.entrySet()) {
            assertTrue(maps2.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), maps2.get(entry.getKey()));
        }
    }

    @Test
    public void get() {

    }


}