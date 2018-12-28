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

    /**
     * how many long integers can hold size bits.
     * @param size
     * @return
     */
    private int sizeFor(int size) {
        int t = size & (64 - 1);
        return t == 0 ? size >>> 6 : ( (size >>> 6) + 1);
    }

    /**
     * set the bit at specific location i to 1.
     * @param i start from 0 to size - 1.
     */
    public void set(int i) {
        int t = i >>> 6;
        i = i & (64 - 1);
        bits[t] |= (1 << i);
    }

    /**
     * set the bit at specific location i to 0.
     * @param i range from [0, size - 1].
     */
    public void clear(int i) {
        int t = i >>> 6;
        i = i & (64 - 1);
        bits[t] &= ~(1 << i);
    }

    /**
     * @param i
     * @return whether the bit at specific location i is set as 1.
     */
    public boolean isSet(int i) {
        return ((bits[i >>> 6]) & (1 << ((i & (64 - 1))))) > 0;
    }

    /**
     * @param i
     * @return whether the bit at specific location i is set as 0.
     */
    public boolean isCleared(int i) {
        return ((bits[i >>> 6]) & (1 << ((i & (64 - 1))))) == 0;
    }

}
