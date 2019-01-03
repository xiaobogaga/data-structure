package com.tomzhu.heap;

/**
 * a simple min-max or max-min heap. A double ended heap can be seen as a union
 * of min heap and max heap. it supports {@code getMax} and {@code getMin} operations
 * with constant time overhead. see {@link } for the original paper of this structure.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicDoubleEndedHeap<E extends Comparable<E>> {

    private int size;
    private Object[] arrs;
    private int capacity = 4;

    /**
     * Heap type holder.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    /**
     * level type.
     */
    enum LevelType {
        MINLEVEL, MAXLEVEL
    }

    private HeapType heapType;

    /**
     * construct a heap with specific heapType.
     * @param heapType
     */
    public BasicDoubleEndedHeap(HeapType heapType) {
        this.heapType = heapType;
        this.arrs = new Object[this.capacity];
    }

    /**
     * @return the min element of this heap, return <tt>null</tt> if isEmpty.
     */
    public E getMin() {
        if (isEmpty())
            return null;
        if (isMinHeap()) {
            return (E) this.arrs[0];
        } else {
            switch (this.size) {
                case 1:
                    return (E) this.arrs[0];
                case 2:
                    return (E) this.arrs[1];
                default:
                    return ((E) this.arrs[1]).compareTo((E) this.arrs[2]) > 0 ? (E) this.arrs[2]
                            : (E) this.arrs[1];
            }
        }
    }

    /**
     * @return the max element of this heap, return <tt>null</tt> if empty.
     */
    public E getMax() {
        if (isEmpty())
            return null;
        if (isMaxHeap()) {
            return (E) this.arrs[0];
        } else {
            switch (this.size) {
                case 1:
                    return (E) this.arrs[0];
                case 2:
                    return (E) this.arrs[1];
                default:
                    return ((E) this.arrs[1]).compareTo((E) this.arrs[2]) < 0 ? (E) this.arrs[2]
                            : (E) this.arrs[1];
            }
        }
    }

    private int findMin(int i) {
        int childC = ((i + 1) << 1) - 1;
        int grandC1 = ((childC + 1) << 1) - 1;
        int grandC2 = ((childC + 2) << 1) - 1;
        if ( (grandC2 + 1) < this.size)
            return min(grandC1, grandC1 + 1, grandC2, grandC2 + 1);
        else if (grandC2 < this.size) {
            return min(grandC1, grandC1 + 1, grandC2, -1);
        } else if ( (grandC1 + 1) < this.size) {
            return min(grandC1, grandC1 + 1, childC + 1, -1);
        } else if (grandC1 < this.size) {
            return min(grandC1, childC + 1, -1, -1);
        } else if ( (childC + 1) < this.size ){
            return min(childC, childC + 1, -1, -1);
        } else if (childC < this.size) {
            return childC;
        } else {
            return i;
        }
    }

    private int min(int i, int j, int t, int z) {
        int ret = i;
        if (j != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[j]) > 0)
            ret = j;
        if (t != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[t]) > 0)
            ret = t;
        if (z != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[z]) > 0)
            ret = z;
        return ret;
    }

    private int max(int i, int j, int t, int z) {
        int ret = i;
        if (j != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[j]) < 0)
            ret = j;
        if (t != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[t]) < 0)
            ret = t;
        if (z != -1 && ((E) this.arrs[ret]).compareTo((E) this.arrs[z]) < 0)
            ret = z;
        return ret;
    }

    private int findMax(int i) {
        int childC = ((i + 1) << 1) - 1;
        int grandC1 = ((childC + 1) << 1) - 1;
        int grandC2 = ((childC + 2) << 1) - 1;
        if ( (grandC2 + 1) < this.size)
            return max(grandC1, grandC1 + 1, grandC2, grandC2 + 1);
        else if (grandC2 < this.size) {
            return max(grandC1, grandC1 + 1, grandC2, -1);
        } else if ( (grandC1 + 1) < this.size) {
            return max(grandC1, grandC1 + 1, childC + 1, -1);
        } else if (grandC1 < this.size) {
            return max(grandC1, childC + 1, -1, -1);
        } else if ( (childC + 1) < this.size ){
            return max(childC, childC + 1, -1, -1);
        } else if (childC < this.size) {
            return childC;
        } else {
            return i;
        }
    }

    /**
     * percolate down from specific location i.
     * @param i
     * @param levelType
     */
    private void percolateDown(int i, LevelType levelType) {
        Object o;
        int q = 0;
        if (levelType == LevelType.MINLEVEL) {
            int min = findMin(i);
            if ((min > ((i + 1) << 1))) {
                // grand children.
                if (((E) this.arrs[min]).compareTo((E) this.arrs[i]) < 0) {
                    o = this.arrs[min];
                    this.arrs[min] = this.arrs[i];
                    this.arrs[i] = o;
                    q = (min - 1) >> 1;
                    if (((E) this.arrs[min]).compareTo((E)this.arrs[q]) > 0) {
                        o = this.arrs[min];
                        this.arrs[min] = this.arrs[q];
                        this.arrs[q] = o;
                    }
                    percolateDown(min, levelType);
                }
            } else {
                // child
                if ( ((E) this.arrs[min]).compareTo((E) this.arrs[i]) < 0) {
                    o = this.arrs[min];
                    this.arrs[min] = this.arrs[i];
                    this.arrs[i] = o;
                }
            }
        } else {
            int max = findMax(i);
            if ((max > ((i + 1) << 1))) {
                // grand children.
                if (((E) this.arrs[max]).compareTo((E) this.arrs[i]) > 0) {
                    o = this.arrs[max];
                    this.arrs[max] = this.arrs[i];
                    this.arrs[i] = o;
                    q = (max - 1) >> 1;
                    if (((E) this.arrs[max]).compareTo((E)this.arrs[q]) < 0) {
                        o = this.arrs[max];
                        this.arrs[max] = this.arrs[q];
                        this.arrs[q] = o;
                    }
                    percolateDown(max, levelType);
                }
            } else {
                // child
                if ( ((E) this.arrs[max]).compareTo((E) this.arrs[i]) > 0) {
                    o = this.arrs[max];
                    this.arrs[max] = this.arrs[i];
                    this.arrs[i] = o;
                }
            }
        }
    }

    /**
     * remove and return the min element of this heap, return <tt>null</tt> if empty.
     *
     * @return the minimum element of this heap
     */
    public E removeMin() {
        if (isEmpty())
            return null;
        int i = 0;
        if (isMinHeap()) {
            i = 0;
        } else {
            switch (this.size) {
                case 1:
                    i = 0;
                    break;
                case 2:
                    i = 1;
                    break;
                default:
                    if (((E) this.arrs[1]).compareTo((E) this.arrs[2]) > 0)
                        i = 2;
                    else
                        i = 1;
            }
        }
        E ele = (E) this.arrs[i];
        Object o = this.arrs[this.size - 1];
        this.arrs[this.size - 1] = null;
        this.size --;
        if (this.size == 0)
            return ele;
        this.arrs[i] = o;
        percolateDown(i, (i == 0) ? (isMinHeap() ?
                LevelType.MINLEVEL : LevelType.MAXLEVEL) : (isMinHeap() ? LevelType.MAXLEVEL :
                LevelType.MINLEVEL));
        return ele;
    }

    /**
     * remove and return the max element of this heap, return <tt>null</tt> if empty.
     *
     * @return the maximum element of this heap
     */
    public E removeMax() {
        if (isEmpty())
            return null;
        int i = 0;
        if (isMaxHeap()) {
            i = 0;
        } else {
            switch (this.size) {
                case 1:
                    i = 0;
                    break;
                case 2:
                    i = 1;
                    break;
                default:
                    if (((E) this.arrs[1]).compareTo((E) this.arrs[2]) < 0)
                        i = 2;
                    else
                        i = 1;
            }
        }
        E ele = (E) this.arrs[i];
        Object o = this.arrs[this.size - 1];
        this.arrs[this.size - 1] = null;
        this.size --;
        if (this.size == 0)
            return ele;
        this.arrs[i] = o;
        percolateDown(i, (i == 0) ? (isMinHeap() ?
                LevelType.MINLEVEL : LevelType.MAXLEVEL) : (isMinHeap() ? LevelType.MAXLEVEL :
                LevelType.MINLEVEL));
        return ele;
    }

    /**
     * expand the array.
     */
    private void expand() {
        Object[] temp = this.arrs;
        this.capacity = this.capacity << 1;
        this.arrs = new Object[this.capacity];
        System.arraycopy(temp, 0, this.arrs, 0, temp.length);
    }

    /**
     * percolate up from specific index under max property.
     * @param t
     */
    private void percolateUpMax(int t) {
        int g = 0;
        Object temp;
        if (t >= 3) {
            g = (((t - 1) >>> 1) - 1) >>> 1;
            if (((E) this.arrs[g]).compareTo((E) this.arrs[t]) < 0) {
                temp = this.arrs[g];
                this.arrs[g] = this.arrs[t];
                this.arrs[t] = temp;
                percolateUpMax(g);
            }
        }
    }

    /**
     * percolate up from specifc index with min property.
     * @param t
     */
    private void percolateUpMin(int t) {
        int g = 0;
        Object temp;
        if (t >= 3) {
            g = (((t - 1) >>> 1) - 1) >>> 1;
            if (((E) this.arrs[g]).compareTo((E) this.arrs[t]) > 0) {
                temp = this.arrs[g];
                this.arrs[g] = this.arrs[t];
                this.arrs[t] = temp;
                percolateUpMin(g);
            }
        }
    }

    /**
     * percolate up from specifc index and level type.
     * @param i
     * @param levelType
     */
    private void percolateUp(int i, LevelType levelType) {
        if (i <= 0)
            return;
        Object temp;
        int t = 0;
        if (levelType == LevelType.MINLEVEL) {
            t = (i - 1) >>> 1;
            if (((E) this.arrs[t]).compareTo((E) this.arrs[i]) < 0) {
                temp = this.arrs[t];
                this.arrs[t] = this.arrs[i];
                this.arrs[i] = temp;
                percolateUpMax(t);
            } else {
                percolateUpMin(i);
            }
        } else {
            t = (i - 1) >>> 1;
            if (((E) this.arrs[t]).compareTo((E) this.arrs[i]) > 0) {
                temp = this.arrs[t];
                this.arrs[t] = this.arrs[i];
                this.arrs[i] = temp;
                percolateUpMin(t);
            } else {
                percolateUpMax(i);
            }
        }
    }

    /**
     * insert an element.
     * @param ele
     */
    public void insert(E ele) {
        if (this.size >= this.capacity) {
            expand();
        }
        this.arrs[this.size] = ele;
        LevelType type = ((int) ( Math.floor(Math.log(this.size + 1) / Math.log(2)) ) & 1) == 0 ? (isMinHeap() ?
                LevelType.MINLEVEL : LevelType.MAXLEVEL) : (isMaxHeap() ? LevelType.MINLEVEL :
                LevelType.MAXLEVEL);
        percolateUp(this.size, type);
        this.size++;
    }

    /**
     * @return whether this heap is emtpy.
     */
    public boolean isEmpty() {
        return this.size == 0;
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

    /**
     * @return the size of the heap.
     */
    public int getSize() {
        return this.size;
    }

}
