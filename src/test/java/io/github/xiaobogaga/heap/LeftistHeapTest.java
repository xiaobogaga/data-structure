package io.github.xiaobogaga.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link LeftistHeap}
 *
 * @author tomzhu.
 * @since 1.7
 */
public class LeftistHeapTest {

    private LeftistHeap<Integer> leftistHeap;
    private int size = 1000;
    private int[] arrs;

    @Test
    public void getMin() throws Exception {
    }

    @Test
    public void removeMin() throws Exception {
    }

    @Test
    public void getMax() throws Exception {
    }

    @Test
    public void removeMax() throws Exception {
        leftistHeap = new LeftistHeap<Integer>(LeftistHeap.HeapType.MAXHEAP);
        arrs = new int[size];
        Random rand = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < size)
            arrs[i++] = (int) (rand.nextDouble() * 100000);
        for(int t : arrs)
            leftistHeap.insert(t);
        Arrays.sort(arrs);
        i = arrs.length - 1;
        while(i >= 0) {
            assertEquals(arrs[i], (int) leftistHeap.getMax());
            assertEquals(arrs[i], (int) leftistHeap.removeMax());
            i--;
        }
        assertTrue(leftistHeap.isEmpty());
    }

    @Test
    public void insert() throws Exception {
        leftistHeap = new LeftistHeap<Integer>(LeftistHeap.HeapType.MINHEAP);
        arrs = new int[size];
        Random rand = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < size)
            arrs[i++] = (int) (rand.nextDouble() * 100000);
        for(int t : arrs)
            leftistHeap.insert(t);
        Arrays.sort(arrs);
        for (int t : arrs) {
            assertEquals(t, (int) leftistHeap.getMin());
            assertEquals(t, (int) leftistHeap.removeMin());
        }
        assertTrue(leftistHeap.isEmpty());
    }

    @Test
    public void merge() throws Exception {

        LeftistHeap<Integer> leftistHeap1 =
                new LeftistHeap<Integer>(LeftistHeap.HeapType.MINHEAP);
        LeftistHeap<Integer> leftistHeap2 =
                new LeftistHeap<Integer>(LeftistHeap.HeapType.MINHEAP);
        Random rand = new Random(System.currentTimeMillis());
        int[] arr1 = new int[size + 10];
        int[] arr2 = new int[size];

        int i = 0;
        while (i < (size + 10)) {
            arr1[i] = (int) (rand.nextDouble() * 10000);
            leftistHeap1.insert(arr1[i++]);
        }

        i = 0;
        while (i < size) {
            arr2[i] = (int) (rand.nextDouble() * 10000000);
            leftistHeap2.insert(arr2[i++]);
        }

        leftistHeap1.merge(leftistHeap2);

        int[] arr3 = new int[2 * size + 10];
        System.arraycopy(arr1, 0, arr3, 0, arr1.length);
        System.arraycopy(arr2, 0, arr3, size + 10, arr2.length);
        Arrays.sort(arr3);
        for (int t : arr3) {
            assertEquals(t, (int) leftistHeap1.getMin());
            assertEquals(t, (int) leftistHeap1.removeMin());
        }
    }

    @Test
    public void isEmpty() throws Exception {
    }

}