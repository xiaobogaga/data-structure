package com.tomzhu.random;

/**
 * a simple treap structure implementation.
 */

public class Treap<K extends Comparable<K>, P extends Comparable<P>> {

    private int size;

    /**
     * Node holder.
     */
    class Node {
        P priority;
        K key;
        Node left;
        Node right;
        Node parent;
        public Node (K key, P priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    /**
     * Heap type. max heap or min heap.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    private Node root;
    private HeapType heapType;


    public Treap(HeapType heapType) {
        this.heapType = heapType;
    }

    public void insert(K key, P p) {
        if (isEmpty()) {
            this.root = new Node(key, p);
            this.size ++;
            return;
        }
        if (isMinHeap()) {
            Node r = this.root;
            int z = 0;
            while (true) {
                if ((z = r.key.compareTo(key)) == 0) {
                    // replace...
                    r.priority = p;
                    // todo, need adjust.
                    return;
                } else if (z > 0) {
                    if (r.left == null)
                        break;
                    else
                        r = r.left;
                } else {
                    if (r.right == null)
                        break;
                    else
                        r = r.right;
                }
            }

            if (z < 0) {
                // inserted as r's right child.
                
            } else {
                // inserted as r's left child.

            }
        } else {

        }

    }

    /**
     * @return whether this treap is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void remove() {

    }

    public K getPriorityMin() {

    }

    public K getPriorityMax() {

    }

    /**
     * @return whether this heap is a min heap.
     */
    public boolean isMinHeap() {
        return this.heapType == HeapType.MINHEAP;
    }

    /**
     * @return whether this heap is a max heap.
     */
    public boolean isMaxHeap() {
        return this.heapType == HeapType.MAXHEAP;
    }

}
