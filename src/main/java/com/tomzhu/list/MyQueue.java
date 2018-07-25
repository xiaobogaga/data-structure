package com.tomzhu.list;

/**
 * Created by tomzhu on 2017/7/11.
 * a simple imprelementation Queue class.
 */
public class MyQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    class Node<E> {

        private E value;
        private Node<E> after;
        private Node<E> before;

        public Node(E value) {
            this.value = value;
            this.after = this.before = null;
        }

        public Node(E value , Node<E> before , Node<E> after) {
            this.value = value;
            this.before = before;
            this.after = after;
        }

        public Node(E value , Node<E> after) {
            this.after = after;
            this.value = value;
            this.before = null;
        }

    }

    public  MyQueue() {}

    /**
     * check whether the queue is empty.
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * return the number of elements this queue holds.
     * @return
     */
    public int getSize() {
        return this.size;
    }

    /**
     * push an element to the queue.
     * @param value
     */
    public void push(E value) {
        Node<E> newHead = new Node<E>(value , this.head);
        if (isEmpty()) {
            this.size++;
            this.head = this.tail = newHead;
            return;
        } else {
            this.head.before = newHead;
            this.head = newHead;
            this.size++;
        }
    }

    /**
     * get an element from the tail of the queue.
     * return null when the queue is empty.
     * @return
     */
    public E deQueue() {
        if (isEmpty())
            return null;
        else {
            if (this.size == 1) {
                this.size--;
                E temp = this.tail.value;
                this.head = this.tail = null;
                return temp;
            } else {
                this.size--;
                E temp = this.tail.value;
                this.tail.before.after = null;
                this.tail = this.tail.before;
                return temp;
            }
        }
    }

    /**
     * return and don't remove the tail of the queue,
     * throw @see{com.tomzhu.list.MyNosuchElementException} when the queue is empty.
     * @return
     * @throws MyNosuchElementException
     */
    public E getTail() throws MyNosuchElementException {
        if (isEmpty())
            throw new MyNosuchElementException("the queue is emtpy");
        else {
            return this.tail.value;
        }
    }

    /**
     * return and don't remove the head of the queue,
     * throw @see{com.tomzhu.list.MyNosuchElementException} when the queue is empty.
     * @return
     * @throws MyNosuchElementException
     */
    public E getHead() throws MyNosuchElementException {
       if (isEmpty())
           throw new MyNosuchElementException("the queue is emtpy");
        else {
           return this.head.value;
       }
    }

}
