package com.tomzhu.tree;

/**
 * Created by tomzhu on 18-4-5.
 * a simple tree set implementation based on BinarySearchTree.
 */
public class TreeSet<E extends Comparable<E>> {

    private BinarySearchTree<E> tree;

    public TreeSet() {
        this.tree = new BinarySearchTree<E>();
    }

    /**
     * check whether this set contains the element.
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return this.tree.contains(element);
    }

    /**
     * try to add an element to this set. return false if the set contains
     * a same element , return true otherwise.
     * @param element
     * @return
     */
    public boolean add(E element) {
        if (this.tree.contains(element)) {
            return false;
        } else {
            this.tree.insert(element);
            return true;
        }
    }

    /**
     * try to remove an element from this set, return false if this set doesn't contain the element.
     * return true otherwise.
     * @param element
     * @return
     */
    public boolean remove(E element) {
        return this.tree.remove(element);
    }



}
