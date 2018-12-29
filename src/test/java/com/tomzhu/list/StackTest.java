package com.tomzhu.list;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * testing {@link Stack}
 */
public class StackTest {

    int size = 100;
    Stack<Integer> stack;

    @Test
    public void isEmpty() {
    }

    @Test
    public void getSize() {
    }

    @Test
    public void pop() {
    }

    @Test
    public void push() {
        stack = new Stack<>();
        assertTrue(stack.isEmpty());
        for (int i = 0; i < size; i++) {
            stack.push(i);
            assertEquals((int) stack.getHead(), i);
        }

        // testing pop
        for (int i = size - 1; i >= 0; i--) {
            assertEquals((int) stack.getHead(), i);
            assertEquals((int) stack.pop(), i);
            assertEquals(stack.getSize(), i);
        }

        assertTrue(stack.isEmpty());

    }

    @Test
    public void getHead() {
    }

}