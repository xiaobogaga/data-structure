package com.tomzhu.list;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * a simple list implementaion with generic type support.
 * @param <E>
 */
public class MyList<E> {

    private int capa = 10;

    private int currentSize = 0;

    private Object[] nodes;

    private int initCap = 10;

    public MyList(int initCapa) {
        this.initCap = this.capa = initCapa;
        nodes = new Object[this.capa];
    }

    public MyList(){
        this.nodes = new Object[this.capa];
    }

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
     * this method insert the object to the tail
     */
    public void insert(E ele) {
        if (!ensureCapa(this.currentSize)) {
            this.increaseCapa();
        }
        this.nodes[this.currentSize++] = ele;
    }

    /**
     * insert an element to specific location,
     * throw @MyNotSupportException when the location i is beyong the current list size by 2.
     * by 1 just insert to tail.
     * @param ele the element to insert
     * @param i the location
     * @throws MyNotSupportException
     */
    public void insert(E ele , int i) throws MyNotSupportException {
        if (i >= currentSize)
            throw new MyNotSupportException("not support insert an element which go beyond the current elements array.");
        if(!ensureCapa(this.currentSize)) {
            increaseCapa();
        }
        moveElement(i , i + 1 , this.currentSize - i);
        this.nodes[i] = ele;
    }

    /**
     * insert an element to tail.
     * @param ele
     */
    public void insertToHead(E ele){
        if(!ensureCapa(this.currentSize)) {
            increaseCapa();
        }
        moveElement(0 , 1 , this.currentSize);
        this.nodes[0] = ele;
    }

    /**
     * move the specific length elements from "from" to "to" , which is useful when insert or remove an element in
     * a specific location.
     * @param from
     * @param to
     * @param length
     */
    private void moveElement(int from, int to, int length) {
        int i = 0;
        while (i < length) {
            this.nodes[to + i] = this.nodes[from + i];
            i++;
        }
    }


    public void replace(E ele , int i) {
        if (!ensureSize(i))
            throw new NoSuchElementException("no such element in the location");
        this.nodes[i] = ele;
    }

    /**
     * remove the tail element
     */
    public E remove() {
        if (isEmpty())
            return null;
        E temp = (E) this.nodes[this.currentSize - 1];
        this.currentSize--;
        return temp;
    }

    public E removeHead() {
        if (isEmpty())
            return null;
        E temp = (E) this.nodes[0];
        this.moveElement(1 , 0 , --this.currentSize);
        return temp;
    }

    public E remove(int i) throws MyNosuchElementException{
        if (ensureSize(i)) {
            E temp = (E) this.nodes[i];
            this.moveElement(i, i - 1, this.currentSize - i);
            this.currentSize--;
            return temp;
        } else {
            throw new MyNosuchElementException("no such element in the location");
        }
    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    public int getSize() {
        return this.currentSize;
    }

    public int getCapa() {
        return this.capa;
    }


    /**
     * search for the specific element, return the location if found or -1 cannot find.
     * @param ele
     * @return
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
     * check whether the specific ele exits , if yes, return true, or false;
     * @param ele
     * @return
     */
    public boolean hasElement(E ele) {
        for(Object o : this.nodes)
            if (o.equals(ele))
                return true;
        return false;
    }

    /**
     * sort the array
     */
    public void sort() {
        Arrays.sort(this.nodes);
    }

    /**
     * we think this method is useful in some situation.
     */
    public Object[] getArray() {
        return  this.nodes;
    }

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


    public E getTail() {
        if (isEmpty())
            return null;
        return (E) this.nodes[this.currentSize - 1];
    }

    public E getHead() {
        if (isEmpty())
            return null;
        return (E) this.nodes[0];
    }



}
