package io.github.xiaobogaga.heap;

import java.util.Arrays;

/**
 * a simple heap implementation based on array which can be max heap or min heap.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicArrayHeap<E extends Comparable<E>> {

    /**
     * heap type holder.
     */
    public enum HeapType {
        MAXHEAP, MINHEAP
    }

    private HeapType heapType;
    private Object[] arrs;
    private int size = 1;
    private int capacity = (1 << 2);

    /**
     * construct a heap with specific heapType.
     * @param heapType
     */
    public BasicArrayHeap(HeapType heapType) {
        this.heapType = heapType;
        this.arrs = new Object[this.capacity + 1];
    }

    /**
     * construct a head with specific heap type and initial capacity.
     * @param heapType
     * @param initialCapacity
     */
    private BasicArrayHeap(HeapType heapType, int initialCapacity) {
        this.heapType = heapType;
        this.capacity = initialCapacity;
        this.arrs = new Object[this.capacity + 1];
    }

    /**
     * building a heap using data within O(N) time.
     *
     * @param <T> the type of element
     * @param eles the elements
     * @param heapType heap type
     * @return the built heap
     */
    public static <T extends Comparable<T>> BasicArrayHeap buildHeap(T[] eles, HeapType heapType) {
        BasicArrayHeap<T> arrayHeap = new BasicArrayHeap<T>(heapType, eles.length);
        int i = 1;
        for (T ele : eles)
            arrayHeap.arrs[i++] = ele;
        arrayHeap.size = arrayHeap.capacity + 1;
        for(i = (arrayHeap.size - 1) >>> 1; i > 0; i--) {
            arrayHeap.percolateDown(i);
        }
        return arrayHeap;
    }

    /**
     * perform percolate down from location i.
     * @param l
     */
    private void percolateDown(int l) {
        int r = 0;
        if (isMinHeap()) {
            Object o = this.arrs[l];
            for(; (l << 1) < this.size; l = r) {
                r = l << 1;
                if (r + 1 < this.size &&
                        ((E) this.arrs[r]).compareTo((E) this.arrs[r + 1]) > 0)
                    r = r + 1;
                if (((E) this.arrs[r]).compareTo((E) o) < 0)
                    this.arrs[l] = this.arrs[r];
                else
                    break;
            }
            this.arrs[l] = o;
        } else {
            Object o = this.arrs[l];
            for(; (l << 1) < this.size; l = r) {
                r = l << 1;
                if (r + 1 < this.size &&
                        ((E) this.arrs[r]).compareTo((E) this.arrs[r + 1]) < 0)
                    r = r + 1;
                if (((E) this.arrs[r]).compareTo((E) o) > 0)
                    this.arrs[l] = this.arrs[r];
                else
                    break;
            }
            this.arrs[l] = o;
        }
    }

    /**
     * @return whether this heap is a max heap
     */
    public boolean isMaxHeap() {
        return this.heapType == HeapType.MAXHEAP;
    }

    /**
     * @return whether this heap is a min heap
     */
    public boolean isMinHeap() {
        return this.heapType == HeapType.MINHEAP;
    }

    private void resize() {
        this.capacity <<= 1;
        Object[] old = this.arrs;
        this.arrs = new Object[this.capacity + 1];
        System.arraycopy(old, 0, this.arrs, 0, old.length);
    }

    /**
     * insert an element to this heap.
     * @param ele
     */
    public void insert(E ele) {
        if (this.size > this.capacity) {
            resize();
        }
        int l = this.size++;
        this.arrs[0] = ele;
        if (isMaxHeap()) {
            for (; ((Comparable<E>) this.arrs[l >>> 1]).compareTo(ele) < 0;
                 l = l >>> 1)
                this.arrs[l] = this.arrs[l >>> 1];
            this.arrs[l] = ele;
        } else {
            for (; ((Comparable<E>) this.arrs[l >>> 1]).compareTo(ele) > 0;
                 l = l >>> 1)
                this.arrs[l] = this.arrs[l >>> 1];
            this.arrs[l] = ele;
        }
    }

    /**
     * @return the max element of this heap, only supports {@link HeapType}.MAXHEAP. return
     * <tt>null</tt> if is empty or is a min heap.
     */
    public E getMax() {
        return (this.isMinHeap() || this.isEmpty()) ? null : (E) this.arrs[1];
    }

    /**
     * @return the min element of this heap. only supports {@link HeapType}.MINHEAP.
     * return <tt>null</tt> if is empty or is a max heap.
     */
    public E getMin() {
        return (this.isMaxHeap() || this.isEmpty()) ? null : (E) this.arrs[1];
    }

    /**
     * remove the min element from this heap and return.
     * return <tt>null</tt> if is max heap or isEmpty.
     *
     * @return the min element.
     */
    public E removeMin() {
        if (this.isMaxHeap() || isEmpty())
            return null;
        E ret = (E) this.arrs[1];
        this.arrs[1] = this.arrs[--this.size];
        this.percolateDown(1);
        return ret;
    }

    /**
     * return the max element from this heap and return.
     * return <tt>null</tt> if is min heap or isEmpty.
     *
     * @return the max element
     */
    public E removeMax() {
        if (this.isMinHeap() || isEmpty())
            return null;
        E ret = (E) this.arrs[1];
        this.arrs[1] = this.arrs[--this.size];
        this.percolateDown(1);
        return ret;
    }

    /**
     * @return the size of current heap
     */
    public int getSize() {
        return this.size - 1;
    }

    /**
     * @return whether this heap is empty.
     */
    public boolean isEmpty() {
        return this.size == 1;
    }

    void printInternalArrs() {
        System.out.println(Arrays.toString(this.arrs));
    }


}
