package com.tomzhu.list;

/**
 * a simple implementation for stack like {@link java.util.Stack}
 *
 * @author tomzhu
 * @since 1.7
 */
public class Stack<E> {

    private Node<E> head;

    private int size = 0;

    /**
     * a simple node for holding element.
     * @param <E>
     */
    class Node<E> {

        private E value;
        private Node<E> after;

        public Node(E value , Node<E> after) {
            this.value = value;
            this.after = after;
        }

    }

    /**
     * @return <tt>true</tt> if stack is empty, return <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * construct a stack.
     */
    public Stack() {}

    /**
     * @return the number of elements the stack holds.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * remove and return the tail element in this stack, return <tt>null</tt> when the stack is empty.
     *
     * @return the tail element
     */
    public E pop() {
        if (isEmpty())
            return null;
        E temp = this.head.value;
        this.head = this.head.after;
        this.size--;
        return temp;
    }

    /**
     * push a element to the stack.
     *
     * @param ele
     */
    public void push(E ele) {
        Node<E> newHead = new Node<E>(ele , this.head);
        this.head = newHead;
        this.size++;
    }

    /**
     * return the tail element in this stack and don't remove it. throw {@link MyNosuchElementException} when
     * the stack is empty.
     *
     * @return the tail element
     */
    public E getHead() {
        if (isEmpty())
            return null;
        return this.head.value;
    }


}
