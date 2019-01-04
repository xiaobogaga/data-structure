package com.tomzhu.tree;

import com.tomzhu.tree.BasicStringTrie;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BasicStringTrie}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicStringTrieTest {

    private BasicStringTrie basicStringTrie;
    private int size = 500;
    private java.util.HashMap<Integer, Integer> maps;

    @Test
    public void buildTrie() throws Exception {

    }

    @Test
    public void contains() throws Exception {
        Random rand = new Random();
        basicStringTrie = new BasicStringTrie();
        maps = new HashMap<>();
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                Object[] arr  = maps.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(basicStringTrie.contains(Integer.toString(k)));
                basicStringTrie.remove(Integer.toString(k));
                assertFalse(basicStringTrie.contains(Integer.toString(k)));
                maps.remove(k);
                i ++;
                continue;
            }
            int k = rand.nextInt(1000);
            if (maps.containsKey(k)) {
                assertTrue(basicStringTrie.contains(Integer.toString(k)));
                continue;
            } else {
                assertFalse(basicStringTrie.contains(Integer.toString(k)));
                maps.put(k, k);
                basicStringTrie.insert(Integer.toString(k));
                assertTrue(basicStringTrie.contains(Integer.toString(k)));
                i ++;
            }
        }
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}