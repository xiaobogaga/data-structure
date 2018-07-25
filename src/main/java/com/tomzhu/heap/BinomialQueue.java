package com.tomzhu.heap;

/**
 * a simple binomial queue implementation.
 *
 * @author tomzhu.
 */
public class BinomialQueue<E extends Comparable<E>> {

    /**
     * HeapType holder.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    /**
     * BinomialTree.
     */
    class BinomialTree {
        private Node root;

        public BinomialTree(Node root) {
            this.root = root;
        }
    }

    /**
     * Node holder.
     */
    class Node {
        private E ele;
        private Node leftChild;
        private Node rightSibling;
        public Node(E ele) {
            this.ele = ele;
        }
    }

    private HeapType heapType;

    private BinomialTree[] trees;

    private int size;

    private int treeSize;

    /**
     * construct a binomial heap with specific heapType.
     *
     * @param heapType
     */
    public BinomialQueue(HeapType heapType) {
        this.heapType = heapType;
        this.treeSize = 4;
        this.trees = new BinomialQueue.BinomialTree[this.treeSize];
    }

    /**
     * @param heapType
     * @param treeSize
     */
    private BinomialQueue(HeapType heapType, int treeSize) {
        this.heapType = heapType;
        this.treeSize = treeSize;
        this.trees = new BinomialQueue.BinomialTree[this.treeSize];
    }

    /**
     * insert one ele.
     * @param ele
     */
    public void insert(E ele) {
        merge(new Node(ele));
    }

    /**
     * @return the min element of this heap, return null if is empty or is a max heap.
     */
    public E getMin() {
        return (isEmpty() || isMaxHeap()) ? null : this.trees[fetchTarget()].root.ele;
    }

    /**
     * get the min or max root from this binomial forest.
     *
     * @return
     */
    private int fetchTarget() {
        Node target = null;
        BinomialTree t = null;
        int i = 0, j = 0;
        for (; i < this.treeSize; i++) {
            if ((t = this.trees[i]) != null) {
                j = i;
                break;
            }
        }
        target = t.root;
        if (isMinHeap()) {
            for (; i < this.treeSize; i++) {
                if (this.trees[i] != null) {
                    if (target.ele.compareTo(this.trees[i].root.ele) > 0) {
                        target = this.trees[i].root;
                        j = i;
                    }
                }
            }
        } else {
            for (; i < this.treeSize; i++) {
                if (this.trees[i] != null) {
                    if (target.ele.compareTo(this.trees[i].root.ele) < 0) {
                        target = this.trees[i].root;
                        j = i;
                    }
                }
            }
        }
        return j;
    }

    /**
     * @return the max element of this heap, return null if is empty or is a min heap.
     */
    public E getMax() {
        return (isMinHeap() || isEmpty()) ? null : this.trees[fetchTarget()].root.ele;
    }

    /**
     * remove this tree's root and construct a new forest using it's children.
     * @param tree
     * @return
     */
    private BinomialQueue<E> generateTrees(int height, BinomialTree tree) {
        Node n = tree.root.leftChild;
        if (n == null)
            return null;
        BinomialQueue queue = new BinomialQueue(this.heapType, height);
        int i = 0;
        Node t;
        while (n != null) {
            t = n.rightSibling;
            n.rightSibling = null;
            queue.trees[i++] = new BinomialTree(n);
            n = t;
        }
        queue.size = (1 << queue.treeSize) - 1;
        return queue;
    }

    /**
     * remove the min element from this heap.
     * @return the min element, null if empty or is max heap.
     */
    public E removeMin() {
        if (isEmpty() || isMaxHeap())
            return null;
        int r = fetchTarget();
        E ret = this.trees[r].root.ele;
        BinomialTree tree = this.trees[r];
        this.trees[r] = null;
        this.size -= (1 << r);
        this.merge(generateTrees(r, tree));
        return ret;
    }

    /**
     * remove the max element from this heap.
     * @return the max element, null if empty or is min heap.
     */
    public E removeMax() {
        if (isEmpty() || isMinHeap())
            return null;
        int r = fetchTarget();
        E ret = this.trees[r].root.ele;
        BinomialTree tree = this.trees[r];
        this.trees[r] = null;
        this.size -= (1 << r);
        this.merge(generateTrees(r, tree));
        return ret;
    }

    /**
     * combine two trees.
     * @param tree1
     * @param tree2
     * @return
     */
    private BinomialTree combine(BinomialTree tree1, BinomialTree tree2) {
        if (isMinHeap()) {
            if (tree1.root.ele.compareTo(tree2.root.ele) > 0) {
                if (tree2.root.leftChild == null)
                    tree2.root.leftChild = tree1.root;
                else {
                    Node n = tree2.root.leftChild;
                    while (n.rightSibling != null)
                        n = n.rightSibling;
                    n.rightSibling = tree1.root;
                }
                return tree2;
            } else {
                if (tree1.root.leftChild == null)
                    tree1.root.leftChild = tree2.root;
                else {
                    Node n = tree1.root.leftChild;
                    while (n.rightSibling != null)
                        n = n.rightSibling;
                    n.rightSibling = tree2.root;
                }
                return tree1;
            }
        } else {
            if (tree1.root.ele.compareTo(tree2.root.ele) < 0) {
                if (tree2.root.leftChild == null)
                    tree2.root.leftChild = tree1.root;
                else {
                    Node n = tree2.root.leftChild;
                    while (n.rightSibling != null)
                        n = n.rightSibling;
                    n.rightSibling = tree1.root;
                }
                return tree2;
            } else {
                if (tree1.root.leftChild == null)
                    tree1.root.leftChild = tree2.root;
                else {
                    Node n = tree1.root.leftChild;
                    while (n.rightSibling != null)
                        n = n.rightSibling;
                    n.rightSibling = tree2.root;
                }
                return tree1;
            }
        }
    }

    /**
     * expand the tree array.
     *
     * @param newTreeSize
     */
    private void expand(int newTreeSize) {
        BinomialTree[] temp = this.trees;
        this.trees = new BinomialQueue.BinomialTree[newTreeSize];
        System.arraycopy(temp, 0, this.trees, 0, temp.length);
        this.treeSize = newTreeSize;
    }

    /**
     * merge this heap with one node.
     * @param newNode
     */
    private void merge(Node newNode) {
        int i = 0;
        int treeSize1 = this.treeSize;
        if (this.size + 1 > ((1 << treeSize1) - 1)) {
            expand(treeSize1 + 1);
        }
        BinomialTree last = new BinomialTree(newNode);
        while (i < this.treeSize) {
            if (this.trees[i] == null) {
                this.trees[i] = last;
                break;
            } else {
                last = combine(this.trees[i], last);
                this.trees[i] = null;
            }
            i++;
        }
        this.size ++;
    }

    /**
     * @param queue merge two heap.
     */
    public void merge(BinomialQueue<E> queue) {
        if (queue == null || queue.isEmpty() ||
                this.heapType != queue.heapType)
            return;
        if (this.isEmpty()) {
            this.trees = queue.trees;
            this.size = queue.size;
            this.treeSize = queue.treeSize;
            return;
        }
        int i = 0;
        int treeSize1 = this.treeSize, treeSize2 = queue.treeSize;
        if (this.size + queue.size > ((1 << treeSize1) - 1)) {
            if (this.size + queue.size > ((1 << treeSize2) - 1))
                expand(Math.max(treeSize1, treeSize2) + 1);
            else
                expand(treeSize2);
        }
        this.size += queue.size;
        BinomialTree last = null;
        while (i < treeSize2) {
            if (last == null) {
                if (this.trees[i] != null) {
                    if (queue.trees[i] != null) {
                        last = combine(this.trees[i], queue.trees[i]);
                        queue.trees[i] = this.trees[i] = null;
                    }
                } else {
                    this.trees[i] = queue.trees[i];
                    queue.trees[i] = null;
                }
            } else {
                if (this.trees[i] != null) {
                    if (queue.trees[i] != null) {
                        last = combine(last, queue.trees[i]);
                        queue.trees[i] = null;
                    } else {
                        last = combine(last, this.trees[i]);
                        this.trees[i] = null;
                    }
                } else {
                    if (queue.trees[i] == null) {
                        this.trees[i] = last;
                        last = null;
                    } else {
                        last = combine(last, queue.trees[i]);
                        queue.trees[i] = null;
                    }
                }
            }
            i++;
        }

        if (last != null) {
            while (i < this.treeSize) {
                if (last == null) {
                    break;
                } else {
                    if (this.trees[i] == null) {
                        this.trees[i] = last;
                        break;
                    } else {
                        last = combine(last, this.trees[i]);
                    }
                }
                i++;
            }
        }

    }

    /**
     * @return whether this heap is emtpy.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return whether this heap is a max heap.
     */
    public boolean isMaxHeap() {
        return this.heapType == HeapType.MAXHEAP;
    }

    /**
     * @return whether this heap is a min heap.
     */
    public boolean isMinHeap() {
        return this.heapType == HeapType.MINHEAP;
    }

}
