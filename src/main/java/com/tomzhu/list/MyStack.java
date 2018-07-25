package com.tomzhu.list;

/**
 * Created by tomzhu on 2017/7/11.
 * a simple implementation for stack
 */
public class MyStack<E> {

    private Node<E> head;

    private int size = 0;

    class Node<E> {

        private E value;
        private Node<E> after;

        public Node(E value , Node<E> after) {
            this.value = value;
            this.after = after;
        }

        public Node(E value) {
            this.value = value;
            this.after = null;
        }

    }

    /**
     * check whether the stack is empty
     * return true when it is empty , otherwise false.
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    public MyStack() {}

    /**
     * @return the number of elements the stack holds.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * remove and return the head element in this stack,
     * return null when the stack is empty.
     * @return the head element
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
     * @param ele
     */
    public void push(E ele) {
        Node<E> newHead = new Node<E>(ele , this.head);
        this.head = newHead;
        this.size++;
    }

    /**
     * return the head element in this stack and don't remove it.
     * throw @see{com.tomzhu.list.MyNosuchElementException} when the stack is empty.
     * @return
     */
    public E getHead() {
        if (isEmpty())
            return null;
        return this.head.value;
    }


}
