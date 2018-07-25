package com.tomzhu.hash;

import com.tomzhu.tree.RedBlackTree;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author tomzhu.
 */
public class ExtensibleHashMapTest {

    private ExtensibleHashMap<Integer, Integer> nameMap;

    private HashMap<Integer, Integer> map;

    private RedBlackTree<Integer> redBlackTree;

    private int[] arr;

    private int size;

    @Test
    public void insert() throws Exception {

        size = 1000;
        arr = new int[size];
        nameMap = new ExtensibleHashMap<Integer, Integer>();
        map = new HashMap<Integer, Integer>();
        redBlackTree = new RedBlackTree<Integer>();

        assertTrue(nameMap.isEmpty());
        int i = 0;
        while (i < size) {
            arr[i] = (int) (Math.random() * 100000);
            nameMap.insert(arr[i], arr[i]);
            redBlackTree.insert(arr[i]);
            i ++;
        }

        // System.out.println(Arrays.toString(arr));

        assertFalse(nameMap.isEmpty());
        // first check contains.
        for (int t : arr)
            assertTrue(nameMap.contains(t));

        // check remove.
        for (int t : arr) {
            if (!map.containsKey(t)) {
           //     System.out.println("t : " + t);
            //    System.out.println(redBlackTree);
                assertTrue(redBlackTree.delete(t));
                assertTrue(nameMap.remove(t));
                map.put(t, t);
            }
            else
                assertFalse(nameMap.remove(t));
        }
        assertTrue(nameMap.isEmpty());
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}