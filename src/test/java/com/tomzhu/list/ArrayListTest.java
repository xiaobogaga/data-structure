package com.tomzhu.list;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link ArrayList}
 */
public class ArrayListTest {

    int size = 1000;
    int[] data;
    ArrayList<Integer> list;
    Random random;

    @Test
    public void get() throws MyNotSupportException, MyNosuchElementException {
        list = new ArrayList<>();
        data = new int[size];
        random = new Random(System.currentTimeMillis());
        assertTrue(list.isEmpty());
        for (int i = 0; i < size; i++) {
            assertEquals(list.getSize(), i);
            list.insert(i, list.getSize());
            assertEquals((int) list.get(list.getSize() - 1), i);
        }

        assertFalse(list.isEmpty());
        for (int i = 0; i < size; i++) {
            int loc = random.nextInt();
            if (loc < 0) loc = -loc;
            loc = loc % list.getSize();
            assertEquals((int) list.get(loc), loc);
            assertEquals(list.search(loc), loc);
        }

        // testing remove
        for (int i = 0; i < size; i++) {
            assertEquals((int) list.remove(0), i);
        }
        assertTrue(list.isEmpty());

        for (int i = 0; i < size; i++) {
            assertEquals(list.getSize(), i);
            list.insert(i, list.getSize());
            assertEquals((int) list.get(list.getSize() - 1), i);
        }

        assertFalse(list.isEmpty());

        //testing replace
        HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();
        for (int i = 0; i < size; i++) {
            int loc = random.nextInt();
            if (loc < 0) loc = -loc;
            loc = loc % list.getSize();
            int value = random.nextInt();
            maps.put(loc, value);
            list.replace(loc, value);
        }

        for (Map.Entry<Integer, Integer> entry : maps.entrySet()) {
            assertEquals(entry.getValue(), list.get(entry.getKey()));
        }

        // testing clear
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void insert() {
    }

    @Test
    public void replace() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void getSize() {
    }

    @Test
    public void search() {
    }

    @Test
    public void clear() {
    }
}