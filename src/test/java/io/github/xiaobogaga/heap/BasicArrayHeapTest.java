package io.github.xiaobogaga.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BasicArrayHeap}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicArrayHeapTest {

    private BasicArrayHeap<Integer> minHeap;

    private BasicArrayHeap<Integer> maxHeap;

    private int size = 200;

    private Integer[] eles;

    @Test
    public void buildHeap() throws Exception {
        maxHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MAXHEAP);
        minHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MINHEAP);
        eles = new Integer[size];
        Random rand = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < size) {
            eles[i++] = (int) (rand.nextDouble() * 1000000);
        }
        minHeap = BasicArrayHeap.buildHeap(eles, BasicArrayHeap.HeapType.MINHEAP);
        maxHeap = BasicArrayHeap.buildHeap(eles, BasicArrayHeap.HeapType.MAXHEAP);

        // test min heap.
        Arrays.sort(eles);
        i = 0;
        while (i < this.eles.length) {
            assertEquals(this.eles[i], minHeap.getMin());
            assertEquals(this.eles[i], minHeap.removeMin());
            i ++;
        }

        // test max heap
        i = this.eles.length - 1;
        while (i >= 0) {
            assertEquals(this.eles[i], maxHeap.getMax());
            assertEquals(this.eles[i--], maxHeap.removeMax());
        }

    }

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void getMax() throws Exception {
        maxHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MAXHEAP);
        minHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MINHEAP);
        eles = new Integer[size];
        Random rand = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < size) {
            eles[i] = (int) (rand.nextDouble() * 1000000);
            minHeap.insert(eles[i]);
            maxHeap.insert(eles[i++]);
        }
        Arrays.sort(eles);

        i = this.eles.length - 1;
        while (i >= 0) {
            assertEquals(this.eles[i], maxHeap.getMax());
            assertEquals(this.eles[i--], maxHeap.removeMax());
        }
    }

    @Test
    public void getMin() throws Exception {
        maxHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MAXHEAP);
        minHeap = new BasicArrayHeap<Integer>(BasicArrayHeap.HeapType.MINHEAP);
        eles = new Integer[size];
        Random rand = new Random(System.currentTimeMillis());
        int i = 0;
        while (i < size) {
            eles[i] = (int) (rand.nextDouble() * 1000000);
            minHeap.insert(eles[i]);
            maxHeap.insert(eles[i++]);
        }
        Arrays.sort(eles);
        i = 0;
        while (i < this.eles.length) {
            assertEquals(this.eles[i], minHeap.getMin());
            assertEquals(this.eles[i], minHeap.removeMin());
            i ++;
        }
    }

    @Test
    public void removeMin() throws Exception {
    }

    @Test
    public void removeMax() throws Exception {
    }

}