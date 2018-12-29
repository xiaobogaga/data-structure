package com.tomzhu.list;

/**
 * a simple Queue implementation.
 *
 * @param <E> the type of element
 * @author tomzhu
 * @since 1.7
 */
public class Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    /**
     * a list node class.
     *
     * @param <E> the type of element.
     */
    class Node<E> {

        private E value;
        private Node<E> after;
        private Node<E> before;

        public Node(E value , Node<E> before) {
            this.after = null;
            this.value = value;
            this.before = before;
        }

    }

    /**
     * construct a queue class
     */
    public Queue() {}

    /**
     * @return <tt>true</tt> if empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return the size of this queue
     */
    public int getSize() {
        return this.size;
    }

    /**
     * add an element to the queue tail.
     *
     * @param value the added element
     */
    public void enQueue(E value) {
        Node<E> newTail = new Node<E>(value , this.tail);
        if (isEmpty()) {
            this.size++;
            this.head = this.tail = newTail;
            return;
        } else {
            this.tail.after = newTail;
            this.tail = newTail;
            this.size++;
        }
    }

    /**
     * return and remove an element from the head of this queue and return <tt>null</tt> when the queue is empty.
     *
     * @return the head of queue.
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
                E temp = this.head.value;
                this.head.after.before = null;
                this.head = this.head.after;
                return temp;
            }
        }
    }

    /**
     * just the head of the queue, throw {@link MyNosuchElementException} when the queue is empty.
     *
     * @return the head of the queue.
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
