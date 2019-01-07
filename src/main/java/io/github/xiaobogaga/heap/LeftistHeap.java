package io.github.xiaobogaga.heap;

/**
 * a simple leftist heap implementation.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class LeftistHeap<E extends Comparable<E>> {

    private int size;
    private Node root;
    private HeapType heapType;

    /**
     * construct a leftist heap with specific heap type
     * @param heapType
     */
    public LeftistHeap(HeapType heapType) {
        this.heapType = heapType;
    }

    /**
     * Heap type.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    /**
     * node holder.
     */
    class Node {
        private E ele;
        private Node left;
        private Node right;
        private int npl;

        public Node(E ele) {
            this.ele = ele;
            this.npl = 0;
        }

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

    /**
     * @return the min element of this heap, return <tt>null</tt> if empty or is a max heap.
     */
    public E getMin() {
        E ret = (this.size == 0 || heapType == HeapType.MAXHEAP ? null : this.root.ele);
        return ret;
    }

    /**
     * remove and return the min element.
     *
     * @return the min element if not empty, return <tt>null</tt> if is empty or is max heap.
     */
    public E removeMin() {
        if (isMaxHeap())
            return null;
        E ele = getMin();
        if (ele != null) {
            this.root = merge(this.root.left, this.root.right);
            this.size--;
        }
        return ele;
    }

    /**
     * @return the max element of this heap, return <tt>null</tt> if empty or is a min heap.
     */
    public E getMax() {
        return this.size == 0 || heapType == HeapType.MINHEAP ? null : this.root.ele;
    }

    /**
     * remove and return the max element.
     *
     * @return the max element of this heap, return <tt>null</tt> if is empty or is min heap.
     */
    public E removeMax() {
        if (isMinHeap())
            return null;
        E ele = getMax();
        if (ele != null) {
            this.root = merge(this.root.left, this.root.right);
            this.size--;
        }
        return ele;
    }

    /**
     * insert an element.
     *
     * @param ele
     */
    public void insert(E ele) {
        if (ele == null)
            return;
        this.root = merge(this.root, new Node(ele));
        this.size++;
    }

    /**
     * the max npl of two nodes.
     *
     * @param node1
     * @param node2
     * @return
     */
    private int MinNPL(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return -1;
        } else {
            return node1.npl >= node2.npl ? node2.npl : node1.npl;
        }
    }

    /**
     * merge heap2 to this heap, after merge,
     *
     * @param heap2
     */
    public void merge(LeftistHeap<E> heap2) {
        if (heap2 == null || heap2.isEmpty() ||
                heap2.heapType != this.heapType)
            return;
        if (this.isEmpty()) {
            this.root = heap2.root;
            this.size = heap2.size;
            return;
        }
        if (this.heapType == HeapType.MINHEAP) {
            // merge larger root heap with the right subheap of smaller root heap.
            if (heap2.root.ele.compareTo(this.root.ele) >= 0) {
                this.root.right = merge(heap2.root, this.root.right);
            } else {
                heap2.root.right = merge(this.root, heap2.root.right);
                this.root = heap2.root;
            }
        } else {
            // merge larger root heap with the right subheap of smaller root heap.
            if (heap2.root.ele.compareTo(this.root.ele) < 0) {
                this.root.right = merge(heap2.root, this.root.right);
            } else {
                heap2.root.right = merge(this.root, heap2.root.right);
                this.root = heap2.root;
            }
        }
        // after merge, verify to do swap.
        if (this.root.left == null || (this.root.left.npl
                < this.root.right.npl)) {
            // do swap.
            Node r = this.root.right;
            this.root.right = this.root.left;
            this.root.left = r;
        }
        this.root.npl = MinNPL(this.root.left, this.root.right) + 1;
        this.size += heap2.size;
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
        Node temp;
        if (this.heapType == HeapType.MINHEAP) {
            if (node1.ele.compareTo(node2.ele) >= 0) {
                node2.right = merge(node1, node2.right);
                temp = node2;
            } else {
                node1.right = merge(node2, node1.right);
                temp = node1;
            }
        } else {
            if (node1.ele.compareTo(node2.ele) < 0) {
                node2.right = merge(node1, node2.right);
                temp = node2;
            } else {
                node1.right = merge(node2, node1.right);
                temp = node1;
            }
        }
        if (temp.left == null || temp.left.npl < temp.right.npl) {
            // do swap.
            Node r = temp.right;
            temp.right = temp.left;
            temp.left = r;
        }
        temp.npl = MinNPL(temp.left, temp.right) + 1;
        return temp;
    }

    /**
     * @return whether this heap is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }


}
