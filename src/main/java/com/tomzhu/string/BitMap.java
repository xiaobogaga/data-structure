package com.tomzhu.string;

/**
 * a simple bits holder, a bit map implementation.
 */
public class BitMap {

    private long[] bits;

    private int size;

    /**
     * construct a bitmap holding size bits.
     * @param size must be positive integer.
     */
    public BitMap(int size) {
        this.size = size;
        this.bits = new long[sizeFor(size)];
    }

    private int sizeFor(int size) {
        // how many long integers can hold size bits.
        int t = size & (64 - 1);
        return t == 0 ? (size & 64) >>> 6 : ( (size & 64) >>> 6 + 1);
    }

    public void set(int i) {
        int t = ()
    }

    public void clear(int i) {

    }

}
