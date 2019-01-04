package com.tomzhu.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;
import static org.junit.Assert.*;

/**
 * testing {@link BasicNumberTrie}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicNumberTrieTest {

    private BasicNumberTrie basicNumberTrie;
    private int size = 500;
    private java.util.HashMap<Integer, Integer> maps;

    @Test
    public void buildBasicNumberTries() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        Random rand = new Random();
        basicNumberTrie = new BasicNumberTrie();
        maps = new HashMap<>();
        for (int i = 0; i < size;) {
            if (i != 0 && i % 5 == 0) {
                Object[] arr  = maps.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(basicNumberTrie.contains(Integer.toString(k)));
                basicNumberTrie.remove(Integer.toString(k));
                assertFalse(basicNumberTrie.contains(Integer.toString(k)));
                maps.remove(k);
                i ++;
                continue;
            }
            int k = rand.nextInt(1000);
            if (maps.containsKey(k)) {
                assertTrue(basicNumberTrie.contains(Integer.toString(k)));
                continue;
            } else {
                assertFalse(basicNumberTrie.contains(Integer.toString(k)));
                maps.put(k, k);
                basicNumberTrie.insert(Integer.toString(k));
                assertTrue(basicNumberTrie.contains(Integer.toString(k)));
                i ++;
            }
        }
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

}