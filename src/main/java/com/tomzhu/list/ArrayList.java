package com.tomzhu.list;

import java.util.Arrays;

/**
 * a simple list implementation based on array.
 *
 * like {@link java.util.ArrayList}, this class would holds elements in an object array,
 * and would automatically extends its memory when the added element exceeds threshold with
 * a factor of 2.
 * @param <E> the type of list element
 *
 * @author tomzhu
 * @since 1.7
 */

public class ArrayList<E> {

    private int capa = 10;

    private int currentSize = 0;

    private Object[] nodes;

    private int initCap = 10;

    /**
     * construct a array list with initial capacity
     * @param initCapa initial capacity
     */
    public ArrayList(int initCapa) {
        this.initCap = this.capa = initCapa;
        nodes = new Object[this.capa];
    }

    /**
     * construct a array list with default capacity 10.
     */
    public ArrayList(){
        this.nodes = new Object[this.capa];
    }

    /**
     * try to get the element at location <tt>i</tt> and throws {@link MyNosuchElementException} when
     * the location is not feasible such as negative or exceeds the current size of the list. if {@code i== 0}, then
     * return the head element and if {@code i == this.getSize() - 1}, then return the tail element.
     *
     * @param i
     * @return the element at location i.
     * @throws MyNosuchElementException
     */
    public E get(int i) throws MyNosuchElementException {
        if (!ensureCapa(i))
            throw new MyNosuchElementException("no such element in the location");
        else {
            return (E) this.nodes[i];
        }
    }

    private boolean ensureSize(int i) {
        return this.currentSize > i;
    }

    /**
     * insert an element to specific location <tt>i</tt>. throw @MyNotSupportException when the location <tt>i</tt> is
     * beyond the current list size. if {@code i == 0}, would insert an element to the tail of this list and if
     * {@code i == this.getSize()}, would just add this element to the tail.
     *
     * @param ele the element to insert
     * @param i the location
     * @throws MyNotSupportException
     */
    public void insert(E ele , int i) throws MyNotSupportException {
        if (i >= currentSize + 1)
            throw new MyNotSupportException("not support insert an element which go beyond the current elements array.");
        if(!ensureCapa(this.currentSize)) {
            increaseCapa();
        }
        System.arraycopy(this.nodes, i, this.nodes, i + 1, this.currentSize - i);
        this.nodes[i] = ele;
        this.currentSize ++;
    }

    /**
     * try to replace the element at location <tt>i</tt> with new element <tt>ele</tt>. throws
     * {@link MyNosuchElementException} when the location <tt>i</tt> is not feasible.
     *
     * @param i
     * @param ele
     */
    public void replace(int i, E ele) throws MyNosuchElementException {
        if (!ensureSize(i))
            throw new MyNosuchElementException("no such element in the location");
        this.nodes[i] = ele;
    }

    /**
     * try to remove and return the element at location <tt>i</tt>, throw {@link MyNosuchElementException} when
     * the location <tt>i</tt> is not feasible. if {@code i == 0}, remove the head element and if
     * {@code i == this.getSize() - 1}, remove the tail element.
     *
     * @param i the location
     * @return the element
     * @throws MyNosuchElementException
     */
    public E remove(int i) throws MyNosuchElementException{
        if (ensureSize(i)) {
            E temp = (E) this.nodes[i];
            System.arraycopy(this.nodes, i + 1, this.nodes, i, this.currentSize - i);
            this.currentSize--;
            return temp;
        } else {
            throw new MyNosuchElementException("no such element in the location");
        }
    }

    /**
     * @return whether this list is empty.
     */
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    /**
     * @return the size of this list
     */
    public int getSize() {
        return this.currentSize;
    }

    /**
     * search for the specific element, return the location if found or -1 cannot find.
     *
     * @param ele
     * @return the location when found, -1 otherwise.
     */
    public int search(E ele) {
        int i = 0;
        int start = 0;
        while (start < this.currentSize) {
            if (this.nodes[start].equals(ele))
                return start;
            start++;
        }
        return -1;
    }

    /**
     * clear this list
     */
    public void clear() {
        this.currentSize = 0;
        this.nodes = new Object[this.initCap];
    }

    private void increaseCapa() {
        this.capa += this.capa >> 1;
        this.nodes = Arrays.copyOf(this.nodes , this.capa);
    }

    private boolean ensureCapa(int i) {
        return this.capa > i;
    }

}
