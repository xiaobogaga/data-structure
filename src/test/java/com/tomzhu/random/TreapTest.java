package com.tomzhu.random;

import com.tomzhu.heap.BasicArrayHeap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @tomzhu.
 */
public class TreapTest {

    private Treap<Integer, Integer> maxTreap;
    private Treap<Integer, Integer> minTreap;
    private int size;
    private HashMap<Integer, Integer> map;
    private HashMap<Integer, Integer> prioMap;

    private BasicArrayHeap<Entry> maxHeap;
    private BasicArrayHeap<Entry> minHeap;

    class Entry implements Comparable<Entry> {
        Integer k;
        Integer p;
        public Entry(Integer k, Integer p) {
            this.k = k;
            this.p = p;
        }

        public int compareTo(Entry o) {
            return p.compareTo(o.p);
        }
    }

    public TreapTest() {

    }

    @Test
    public void insert() throws Exception {
        maxTreap = new Treap<Integer, Integer>(Treap.HeapType.MAXHEAP);
        minTreap = new Treap<Integer, Integer>(Treap.HeapType.MINHEAP);
        maxHeap = new BasicArrayHeap<Entry>(BasicArrayHeap.HeapType.MAXHEAP);
        minHeap = new BasicArrayHeap<Entry>(BasicArrayHeap.HeapType.MINHEAP);

        map = new HashMap<Integer, Integer>();
        prioMap = new HashMap<Integer, Integer>();
        size = 1000;
        int i = 0;
        assertTrue(minTreap.isEmpty());
        assertTrue(maxTreap.isEmpty());
        assertTrue(minTreap.isMinHeap());
        assertTrue(maxTreap.isMaxHeap());
        assertFalse(minTreap.isMaxHeap());
        assertFalse(maxTreap.isMinHeap());
        while (i < size) {
            int key = (int) (Math.random() * 10000000);
            int priority = (int) (Math.random() * 1000);
            if (!map.containsKey(key) && !prioMap.containsKey(priority)) {
                map.put(key, priority);
                prioMap.put(priority, key);
                minTreap.insert(key, priority);
                maxTreap.insert(key, priority);
                minHeap.insert(new Entry(key, priority));
                maxHeap.insert(new Entry(key, priority));
             //   System.out.println(minTreap);
             //   System.out.println("------");
             //   System.out.println(maxTreap);
             //   System.out.println(i);
                i ++;
            }
        }
        // System.out.println(minTreap);
        assertFalse(minTreap.isEmpty());
        for (Integer t : map.keySet())
            assertTrue(minTreap.contains(t));

        for (Integer t : map.keySet()) {
            assertTrue(minTreap.remove(t));
        }

        for (Integer t : map.keySet())
            assertFalse(minTreap.contains(t));

        // System.out.println(minTreap);

        for (Map.Entry<Integer, Integer> t : map.entrySet())
            minTreap.insert(t.getKey(), t.getValue());

        // System.out.println(minTreap);

        i = 0;
        while (i < size) {
            assertEquals(minHeap.getMin().k, minTreap.getPriorityMin());
            assertEquals(minHeap.removeMin().k, minTreap.removeMin());
           // System.out.println(minTreap);
            i++;
        }

        assertTrue(minTreap.isEmpty());

        for (Integer t : map.keySet())
            assertTrue(maxTreap.contains(t));

        for (Integer t : map.keySet())
            assertTrue(maxTreap.remove(t));

        for (Integer t : map.keySet())
            assertFalse(maxTreap.contains(t));

        for (Map.Entry<Integer, Integer> t : map.entrySet())
            maxTreap.insert(t.getKey(), t.getValue());
        // System.out.println(maxTreap);
        i = 0;
        while (i < size) {
            assertEquals(maxHeap.getMax().k, maxTreap.getPriorityMax());
            assertEquals(maxHeap.removeMax().k, maxTreap.removeMax());
            // System.out.println(maxTreap);
            i ++;
        }

        assertTrue(maxTreap.isEmpty());

    }

    @Test
    public void isEmpty() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void getPriorityMin() throws Exception {
    }

    @Test
    public void getPriorityMax() throws Exception {
    }

    @Test
    public void isMinHeap() throws Exception {
    }

    @Test
    public void isMaxHeap() throws Exception {
    }

}