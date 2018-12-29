package com.tomzhu.list;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link LinkedList}
 */
public class LinkedListTest {

    int size = 1000;
    int[] data;
    LinkedList<Integer> list;
    Random random;

    @Test
    public void isEmpty() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void get() throws MyNotSupportException, MyNosuchElementException {
        list = new LinkedList<>();
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

        // testing clear
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void remove() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void getSize() {
    }
}