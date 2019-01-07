package io.github.xiaobogaga.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link SkewHeap}
 *
 * @author tomzhu
 * @since 1.7
 */
public class SkewHeapTest {

    private SkewHeap<Integer> minSkewHeap;
    private SkewHeap<Integer> maxSkewHeap;
    private int size = 100;
    private int[] arrs;

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void getMin() throws Exception {
    }

    @Test
    public void isMinHeap() throws Exception {
    }

    @Test
    public void isMaxHeap() throws Exception {
    }

    @Test
    public void getMax() throws Exception {
    }

    @Test
    public void removeMin() throws Exception {
        arrs = new int[size];
        minSkewHeap = new SkewHeap<Integer>(SkewHeap.HeapType.MINHEAP);
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(minSkewHeap.isEmpty());
        assertTrue(minSkewHeap.isMinHeap());
        assertFalse(minSkewHeap.isMaxHeap());
        int i = 0;
        while (i < size) {
            arrs[i] = (int) (rand.nextDouble() * 100000);
            minSkewHeap.insert(arrs[i++]);
        }
        Arrays.sort(arrs);
        assertNull(minSkewHeap.getMax());
        i = 0;
        while (i < size) {
            assertEquals(arrs[i], (int) minSkewHeap.getMin());
            assertEquals(arrs[i++], (int) minSkewHeap.removeMin());
        }
        assertTrue(minSkewHeap.isEmpty());
    }

    @Test
    public void removeMax() throws Exception {
        arrs = new int[size];
        maxSkewHeap = new SkewHeap<Integer>(SkewHeap.HeapType.MAXHEAP);
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(maxSkewHeap.isEmpty());
        assertTrue(maxSkewHeap.isMaxHeap());
        assertFalse(maxSkewHeap.isMinHeap());
        int i = 0;
        while (i < size) {
            arrs[i] = (int) (rand.nextDouble() * 100000);
            maxSkewHeap.insert(arrs[i++]);
        }
        Arrays.sort(arrs);
        assertNull(maxSkewHeap.getMin());
        i = size - 1;
        while (i >= 0) {
            assertEquals(arrs[i], (int) maxSkewHeap.getMax());
            assertEquals(arrs[i--], (int) maxSkewHeap.removeMax());
        }
        assertTrue(maxSkewHeap.isEmpty());
    }

    @Test
    public void merge() throws Exception {
        SkewHeap<Integer> skewHeap1 =
                new SkewHeap<Integer>(SkewHeap.HeapType.MINHEAP);
        SkewHeap<Integer> skewHeap2 =
                new SkewHeap<Integer>(SkewHeap.HeapType.MINHEAP);
        Random rand = new Random(System.currentTimeMillis());
        int[] arr1 = new int[size + 10];
        int[] arr2 = new int[size];

        int i = 0;
        while (i < (size + 10)) {
            arr1[i] = (int) (rand.nextDouble() * 10000);
            skewHeap1.insert(arr1[i++]);
        }

        i = 0;
        while (i < size) {
            arr2[i] = (int) (rand.nextDouble() * 10000000);
            skewHeap2.insert(arr2[i++]);
        }
        skewHeap1.merge(skewHeap2);

        int[] arr3 = new int[2 * size + 10];
        System.arraycopy(arr1, 0, arr3, 0, arr1.length);
        System.arraycopy(arr2, 0, arr3, size + 10, arr2.length);
        Arrays.sort(arr3);
        for (int t : arr3) {
            assertEquals(t, (int) skewHeap1.getMin());
            assertEquals(t, (int) skewHeap1.removeMin());
        }
    }

    @Test
    public void isEmpty() throws Exception {
    }

}