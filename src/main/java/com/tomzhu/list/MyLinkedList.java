package com.tomzhu.list;

import java.util.NoSuchElementException;

/**
 * This class is a bidirectional linked list
 */
public class MyLinkedList<E> {

    /**
     * a simple node class which holds the before , after link and the node value.
     * @param <E>
     */
    class Node<E> {

        public E value;
        public Node<E> before;
        public Node<E> after;

        public Node(E value) {
            this.before = this.after = null;
            this.value = value;
        }

        public Node(E value , Node<E> before , Node<E> after) {
            this.value = value;
            this.before = before;
            this.after = after;
        }

        public Node(E value , Node<E> before) {
            this.value = value;
            this.before = before;

        }

    }

    private Node<E> head;
    private Node<E> tail;

    private int size;

    /**
     * initialize a linkedlist
     */
    public MyLinkedList() {
        this.head = this.tail = null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }


    /**
     * insert an element to head.
     * @param value
     */
    public void insertToHead(E value) {
        if (isEmpty()) {
            this.head = new Node<E>(value , null , null);
            this.tail = this.head;
            this.size = 1;
        } else {
            Node<E> newHead = new Node(value, null, this.head);
            this.head.before = newHead;
            this.head = newHead;
            this.size++;
        }
    }

    /**
     * insert an element "value" to the specific location "i", throw
     * an MyNotSupportException when the location i is not reliable.
     * @param value
     * @param i
     * @throws MyNotSupportException
     */
    public void insert(E value , int i) throws MyNotSupportException {
        if (this.size < i)
            throw new MyNotSupportException("the inserting location is not reliable");
        else {
            if (i == 0)
                insertToHead(value);
            else if (i == this.size)
                insertToTail(value);
            else {
                int start = 0;
                Node<E> temp = head;
                while (start < i) {
                    temp = temp.after;
                    start++;
                }
                Node<E> newNode = new Node<E>(value, temp.before, temp);
                newNode.before.after = newNode;
                newNode.after.before = newNode;
                this.size++;
            }
        }
    }

    /**
     * insert an element to the tail.
     * @param value
     */
    public void insertToTail(E value) {
        if (isEmpty()) {
            this.tail = new Node<E>(value , null , null);
            this.head = this.tail;
            this.size = 1;
        } else {
            Node<E> newTail = new Node<E>(value , this.tail , null);
            this.tail.after = newTail;
            this.tail = newTail;
            this.size++;
        }
    }

    /**
     * return the head element.
     * @return
     */
    public E getHead() {

        if (isEmpty())
            return null;

        return this.head.value;
    }

    /**
     * return the tail element.
     * @return
     */
    public E getTail() {
        if (isEmpty())
            return null;
        return this.tail.value;
    }


    private boolean ensureSize(int i) {
        return this.size > i;
    }

    /**
     * return the element in location "i" , throw MyNosuchElementException when
     * the location i is not reliable.
     * @param i
     * @return
     * @throws MyNosuchElementException
     */
    public E get(int i) throws MyNosuchElementException {
        if (!ensureSize(i))
            throw new MyNosuchElementException("cannot find the element in specific location. i is too large");
        else {
            int start = 0;
            Node<E> temp = head;
            while(start < i) {
                temp = temp.after;
                start++;
            }
            return temp.value;
        }
    }

    /**
     * remove and return the head element , throw MyNosuchElementException when
     * the list is empty.
     * @return
     * @throws MyNosuchElementException
     */
    public E removeHead() throws MyNosuchElementException {
        if (isEmpty())
            throw new MyNosuchElementException("the list is empty");
        else {
            Node<E> temp = this.head;
            if (this.size == 1)
                this.head = this.tail = null;
            else {
                this.head = this.head.after;
                this.head.before = null;
            }
            this.size --;
            return temp.value;
        }
    }

    /**
     * remove and return the tail element , throw MyNosuchElementException when
     * the list is empty.
     * @return
     * @throws MyNosuchElementException
     */
    public E removeTail() throws MyNosuchElementException {
        if (isEmpty())
            throw new MyNosuchElementException("the list is empty");
        else {
            Node<E> temp = this.tail;
            if (this.size == 1) {
                this.head= this.tail = null;
            } else {
                this.tail = this.tail.before;
                this.tail.after = null;
            }
            this.size --;
            return temp.value;
        }
    }

    /**
     * remove and return the element in specific location "i" , throw MyNosuchElementException
     * when the location "i" is not reliable.
     * @param i
     * @return
     * @throws MyNosuchElementException
     */
    public E remove(int i) throws MyNosuchElementException {
        if (!this.ensureSize(i))
            throw new MyNosuchElementException("there is no such element in the specific location");
        else {
            if (i == 0)
                return removeHead();
            else if (i == this.size - 1)
                return removeTail();
            else {
                Node<E> temp = head;
                int start = 0;
                while (start < i) {
                    temp = temp.after;
                    start++;
                }
                temp.before.after = temp.after;
                temp.after.before = temp.before;
                return temp.value;
            }
        }
    }


    /**
     * clear the list.
     */
    public void clear() {
        this.size = 0;
        this.head = null;
    }

    /**
     * return the current size of the list.
     * @return
     */
    public int getSize() {
        return this.size;
    }

}
