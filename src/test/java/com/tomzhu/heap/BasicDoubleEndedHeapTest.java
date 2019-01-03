package com.tomzhu.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BasicDoubleEndedHeap}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicDoubleEndedHeapTest {

    private BasicDoubleEndedHeap<Integer> maxHeap;

    private BasicDoubleEndedHeap<Integer> minHeap;

    private int size = 1000;

    private int[] arr;

    @Test
    public void removeMin() throws Exception {
        minHeap = new
                BasicDoubleEndedHeap<Integer>(BasicDoubleEndedHeap.HeapType.MINHEAP);
        arr = new int[size];
        Random rand = new Random(System.currentTimeMillis());
        int[] arr2 = new int[size];
        assertTrue(minHeap.isEmpty());
        assertNull(minHeap.getMax());
        assertNull(minHeap.getMin());
        assertTrue(minHeap.isMinHeap());
        assertFalse(minHeap.isMaxHeap());
        int i = 0;
        while (i < size) {
            arr[i] = (int) (rand.nextDouble() * 100000);
            minHeap.insert(arr[i++]);
        }
       // System.out.println(Arrays.toString(arr));
        System.arraycopy(arr, 0, arr2, 0, size);
        assertFalse(minHeap.isEmpty());
        Arrays.sort(arr);
        i = 0;
        while (i < size) {
            assertEquals(arr[i], (int) minHeap.getMin());
            assertEquals(arr[i++], (int) minHeap.removeMin());
        }

        i = 0;
        while (i < size) {
            minHeap.insert(arr2[i++]);
        }

        // System.out.println("finish here");
        i = size - 1;
        while (i >= 0) {
            assertEquals(arr[i], (int) minHeap.getMax());
            assertEquals(arr[i--], (int) minHeap.removeMax());
        }
    }

    @Test
    public void removeMax() throws Exception {
        maxHeap = new
                BasicDoubleEndedHeap<Integer>(BasicDoubleEndedHeap.HeapType.MAXHEAP);
        arr = new int[size];
        Random rand = new Random(System.currentTimeMillis());
        int[] arr2 = new int[size];
        assertTrue(maxHeap.isEmpty());
        assertNull(maxHeap.getMax());
        assertNull(maxHeap.getMin());
        assertTrue(maxHeap.isMaxHeap());
        assertFalse(maxHeap.isMinHeap());
        int i = 0;
        while (i < size) {
            arr[i] = (int) (rand.nextDouble() * 100000);
            maxHeap.insert(arr[i++]);
        }
        System.arraycopy(arr, 0, arr2, 0, size);
        assertFalse(maxHeap.isEmpty());
        Arrays.sort(arr);
        i = 0;
        while (i < size) {
            assertEquals(arr[i], (int) maxHeap.getMin());
            assertEquals(arr[i], (int) maxHeap.removeMin());
            i++;
        }

        i = 0;
        while (i < size) {
            maxHeap.insert(arr2[i++]);
        }

        i = size - 1;
        while (i >= 0) {
            assertEquals(arr[i], (int) maxHeap.getMax());
            assertEquals(arr[i--], (int) maxHeap.removeMax());
        }
    }

}