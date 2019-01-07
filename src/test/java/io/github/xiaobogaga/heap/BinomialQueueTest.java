package io.github.xiaobogaga.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BinomialQueue}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BinomialQueueTest {
    private BinomialQueue<Integer> maxHeap;
    private BinomialQueue<Integer> minHeap;
    private int size;
    private int[] arr;

    @Test
    public void removeMin() throws Exception {
        this.size = 1000;
        this.arr = new int[this.size];
        this.minHeap = new BinomialQueue<Integer>(BinomialQueue.HeapType.MINHEAP);
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(minHeap.isEmpty());
        assertFalse(minHeap.isMaxHeap());
        assertTrue(minHeap.isMinHeap());
        int i = 0;
        while (i < size) {
            arr[i] = (int) (rand.nextDouble() * 100000);
            this.minHeap.insert(arr[i++]);
        }
        Arrays.sort(arr);
        assertNull(this.minHeap.getMax());
        assertNull(this.minHeap.removeMax());
        for (int t : arr) {
            assertEquals((int) this.minHeap.getMin(), t);
            assertEquals((int) this.minHeap.removeMin(), t);
        }
    }

    @Test
    public void removeMax() throws Exception {
        this.size = 100;
        this.arr = new int[this.size];
        this.maxHeap = new BinomialQueue<Integer>(BinomialQueue.HeapType.MAXHEAP);
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(maxHeap.isEmpty());
        assertFalse(maxHeap.isMinHeap());
        assertTrue(maxHeap.isMaxHeap());
        int i = 0;
        while (i < size) {
            arr[i] = (int) (rand.nextDouble() * 100000);
            this.maxHeap.insert(arr[i++]);
        }
        Arrays.sort(arr);
        assertNull(this.maxHeap.getMin());
        assertNull(this.maxHeap.removeMin());
        i = size - 1;
        while (i >= 0) {
            assertEquals((int) this.maxHeap.getMax(), arr[i]);
            assertEquals((int) this.maxHeap.removeMax(), arr[i--]);
        }
    }

    @Test
    public void merge() throws Exception {
    }

}