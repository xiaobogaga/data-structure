package com.tomzhu.bitmap;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BitMap}
 *
 * @author tomzhu.
 */
public class BitMapTest {

    private BitMap bitMap;
    private int size = 100;
    private HashMap<Integer, Integer> maps;


    @Test
    public void set() throws Exception {
        maps = new HashMap<>();
        bitMap = new BitMap(1000);
        Random rand = new Random();
        for (int i = 0; i < size;) {
            int k = rand.nextInt(1000 - 1);
            if (i != 0 && i % 5 == 0) {
                Object[] arr = maps.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                loc = (int) arr[loc % arr.length];
                assertTrue(bitMap.isSet(loc));
                bitMap.clear(loc);
                assertFalse(bitMap.isSet(loc));
                assertTrue(bitMap.isCleared(loc));
                maps.remove(loc);
                i ++;
                continue;
            }
            if (maps.containsKey(k)) {
                assertTrue(bitMap.isSet(k));
                assertFalse(bitMap.isCleared(k));
                continue;
            } else {
                assertFalse(bitMap.isSet(k));
                assertTrue(bitMap.isCleared(k));
                bitMap.set(k);
                assertTrue(bitMap.isSet(k));
                assertFalse(bitMap.isCleared(k));
                maps.put(k, k);
                i ++;
            }
        }
    }

    @Test
    public void clear() throws Exception {
        bitMap = new BitMap(1000);
        bitMap.set(924);
        assertTrue(bitMap.isSet(924));
    }

    @Test
    public void isSet() throws Exception {
    }

    @Test
    public void isCleared() throws Exception {
    }

}