package com.tomzhu.string;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tomzhu.
 */
public class BitMapTest {

    private BitMap bitMap1;
    private BitMap bitMap2;
    private BitMap bitMap3;


    @Test
    public void set() throws Exception {

        bitMap1 = new BitMap(62);

        bitMap2 = new BitMap(122);

        bitMap3 = new BitMap(1006);

        assertTrue(bitMap1.isCleared(10));
        assertTrue(bitMap2.isCleared(79));
        assertTrue(bitMap3.isCleared(998));

        bitMap1.set(18);
        bitMap1.set(61);
        assertTrue(bitMap1.isSet(18));
        assertTrue(bitMap1.isSet(61));
        assertFalse(bitMap1.isSet(17));

        bitMap1.clear(18);
        bitMap1.clear(61);
        assertTrue(bitMap1.isCleared(18));
        assertTrue(bitMap1.isCleared(61));

        bitMap2.set(113);
        bitMap2.set(98);
        assertTrue(bitMap2.isSet(113));
        assertTrue(bitMap2.isSet(98));
        assertFalse(bitMap2.isSet(76));

        bitMap2.clear(113);
        assertTrue(bitMap2.isCleared(113));
        bitMap2.clear(98);
        assertTrue(bitMap2.isCleared(98));

        bitMap2.set(45);
        assertTrue(bitMap2.isSet(45));
        bitMap2.clear(45);
        assertTrue(bitMap2.isCleared(45));

        bitMap3.set(1000);
        bitMap3.set(500);
        assertTrue(bitMap3.isSet(1000));
        assertTrue(bitMap3.isSet(500));

        bitMap3.clear(1000);
        bitMap3.clear(500);
        assertTrue(bitMap3.isCleared(1000));
        assertTrue(bitMap3.isCleared(500));

        bitMap2.set(121);
        assertTrue(bitMap2.isSet(121));
        bitMap2.clear(121);
        assertTrue(bitMap2.isCleared(121));

        bitMap3.set(1005);
        assertTrue(bitMap3.isSet(1005));
        bitMap3.clear(1005);
        assertTrue(bitMap3.isCleared(1005));



    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void isSet() throws Exception {
    }

    @Test
    public void isCleared() throws Exception {
    }

}