package com.tomzhu.tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author tomzhu.
 */
public class RedBlackTreeTest {

    private RedBlackTree<Integer> redBlackTree;

    private int size = 1000;

    private HashMap<Integer, Integer> map;


    @Test
    public void insert() throws Exception {
        redBlackTree = new RedBlackTree<Integer>();
        map = new HashMap<Integer, Integer>();
        int i = 0;
        assertTrue(redBlackTree.isEmpty());
        while (i < size) {
            int v = (int) (Math.random() * 100000);
            if (!map.containsKey(v)) {
                map.put(v, v);
                redBlackTree.insert(v);
                i++;
            }
        }
        assertFalse(redBlackTree.isEmpty());
        Set<Integer> sets = map.keySet();
        Object[] arr = sets.toArray();
        for (Object t : arr)
            assertTrue(redBlackTree.contains((Integer) t));
        Arrays.sort(arr);
        i = 0;
        while (i < size) {
            assertEquals(arr[i], (int) redBlackTree.getMin());
            i ++;
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            assertTrue(redBlackTree.remove(entry.getKey()));
            assertFalse(redBlackTree.contains(entry.getKey()));
        }
        assertTrue(redBlackTree.isEmpty());

        i = 0;
        for (Integer t : map.keySet())
            redBlackTree.insert(t);
        assertFalse(redBlackTree.isEmpty());
        i = 0;
        while (i < size) {
            assertTrue(redBlackTree.contains((Integer) arr[i++]));
        }
        i = size - 1;
        while (i >= 0) {
            assertEquals(arr[i], (int) redBlackTree.getMax());
            assertTrue(redBlackTree.remove((Integer) arr[i]));
            i --;
        }
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

}