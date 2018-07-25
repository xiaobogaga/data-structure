package com.tomzhu.heap;

/**
 * @author tomzhu.
 */
public class SkewHeap<E extends Comparable<E>> {

    private int size;
    private Node root;
    private HeapType heapType;

    /**
     * Node holder.
     */
    class Node {
        private E ele;
        private Node left;
        private Node right;

        /**
         * construct a node with specific element value.
         *
         * @param ele
         */
        public Node(E ele) {
            this.ele = ele;
        }
    }

    /**
     * heap type.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    /**
     * construct a skew heap.
     *
     * @param heapType
     */
    public SkewHeap(HeapType heapType) {
        this.heapType = heapType;
    }

    /**
     * insert the ele to this heap.
     *
     * @param ele
     */
    public void insert(E ele) {
        this.root = merge(this.root, new Node(ele));
        this.size++;
        return;
    }

    /**
     * @return the min element of this heap.
     */
    public E getMin() {
        return (isEmpty() || isMaxHeap()) ? null : this.root.ele;
    }

    /**
     * @return whether this heap is min heap.
     */
    public boolean isMinHeap() {
        return this.heapType == HeapType.MINHEAP;
    }

    /**
     * @return whether this heap is max heap.
     */
    public boolean isMaxHeap() {
        return this.heapType == HeapType.MAXHEAP;
    }

    /**
     * @return the max element of this heap.
     */
    public E getMax() {
        return (isMinHeap() || isEmpty()) ? null : this.root.ele;
    }

    /**
     * remove the min element from this heap and return, return null if empty or is a max heap.
     *
     * @return
     */
    public E removeMin() {
        if (isMaxHeap() || isEmpty())
            return null;
        E ret = this.root.ele;
        this.root = merge(this.root.left, this.root.right);
        this.size--;
        return ret;
    }

    /**
     * return the max element from this heap and return, return null if empty or is a min heap.
     *
     * @return
     */
    public E removeMax() {
        if (isMinHeap() || isEmpty())
            return null;
        E ret = this.root.ele;
        this.root = merge(this.root.left, this.root.right);
        this.size--;
        return ret;
    }

    public void merge(SkewHeap<E> skewHeap2) {
        if (this.heapType != skewHeap2.heapType)
            return;
        // try merge.
        if (skewHeap2 == null || skewHeap2.isEmpty())
            return;
        if (isEmpty()) {
            this.root = skewHeap2.root;
            this.size = skewHeap2.size;
            return;
        }
        if (this.isMinHeap()) {
            // merge the larger root with the right sub heap of smaller root heap.
            if (this.root.ele.compareTo(skewHeap2.root.ele) > 0) {
                skewHeap2.root.right = merge(this.root, skewHeap2.root.right);
                this.root = skewHeap2.root;
            } else {
                this.root.right = merge(skewHeap2.root, this.root.right);
            }
        } else {
            // merge the larger root with the right sub heap of smaller root heap.
            if (this.root.ele.compareTo(skewHeap2.root.ele) < 0) {
                skewHeap2.root.right = merge(this.root, skewHeap2.root.right);
                this.root = skewHeap2.root;
            } else {
                this.root.right = merge(skewHeap2.root, this.root.right);
            }
        }
        // do swap unconditionally.
        swap(this.root);
        this.size += skewHeap2.size;
    }

    /**
     * swap this node's two children.
     *
     * @param t
     */
    private void swap(Node t) {
        Node temp = t.right;
        t.right = t.left;
        t.left = temp;
    }

    /**
     * merge two nodes recursively.
     *
     * @param node1
     * @param node2
     * @return
     */
    private Node merge(Node node1, Node node2) {
        if (node1 == null)
            return node2;
        if (node2 == null)
            return node1;
        if (this.isMinHeap()) {
            if (node1.ele.compareTo(node2.ele) > 0) {
                node2.right = merge(node1, node2.right);
                swap(node2);
                return node2;
            } else {
                node1.right = merge(node2, node1.right);
                swap(node1);
                return node1;
            }
        } else {
            if (node1.ele.compareTo(node2.ele) < 0) {
                node2.right = merge(node1, node2.right);
                swap(node2);
                return node2;
            } else {
                node1.right = merge(node2, node1.right);
                swap(node1);
                return node1;
            }
        }
    }

    /**
     * @return whether the heap is emtpy.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

}
