package com.tomzhu.list;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * testing {@link Queue}
 */
public class QueueTest {

    int size = 100;
    Queue<Integer> queue;

    @Test
    public void isEmpty() {
    }

    @Test
    public void getSize() {
    }

    @Test
    public void enQueue() throws MyNosuchElementException {
        queue = new Queue<>();
        assertTrue(queue.isEmpty());
        for (int i = size - 1; i >= 0; i--) {
            queue.enQueue(i);
            assertEquals(queue.getSize(), size - i);
            assertEquals((int) queue.getHead(), size - 1);
        }

        assertFalse(queue.isEmpty());
        // testing dequeue
        for (int i = 0; i < size; i++) {
            assertEquals(queue.getSize(), size - i);
            assertEquals((int) queue.deQueue(), size - 1 - i);
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    public void deQueue() {
    }

    @Test
    public void getHead() {
    }

}