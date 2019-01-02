package com.tomzhu.tree;

/**
 * a simple tree set implementation based on BinarySearchTree.
 *
 * @param <E> the element type
 *
 * @author tomzhu
 * @since 1.7
 */
public class TreeSet<E extends Comparable<E>> {

    private BinarySearchTree<E> tree;

    /**
     * construct a TreeSet.
     */
    public TreeSet() {
        this.tree = new BinarySearchTree<E>();
    }

    /**
     * @param element
     * @return whether this set contains the element.
     */
    public boolean contains(E element) {
        return this.tree.contains(element);
    }

    /**
     * try to add an element to this set. return false if the set contains
     * the element , return true if succeed.
     *
     * @param element
     * @return false if the set contains the element , return true if succeed.
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
     *
     * @param element
     * @return false if this set doesn't contain the element. return true otherwise.
     */
    public boolean remove(E element) {
        return this.tree.remove(element);
    }

}
