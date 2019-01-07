package io.github.xiaobogaga.list;

/**
 * a bidirectional linked list like {@link java.util.LinkedList}
 *
 * @param <E> the type of element.
 *
 * @author tomzhu
 * @since 1.7
 */
public class LinkedList<E> {

    /**
     * a simple node class which holds the before , after link and the node value.
     *
     * @param <E>
     */
    class Node<E> {

        public E value;
        public Node<E> before;
        public Node<E> after;

        public Node(E value, Node<E> before, Node<E> after) {
            this.value = value;
            this.before = before;
            this.after = after;
        }

    }

    private Node<E> head;
    private Node<E> tail;

    private int size;

    /**
     * initialize a linkedlist
     */
    public LinkedList() {
        this.head = this.tail = null;
    }

    /**
     * @return whether this list is empty
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * insert an element <tt>value</tt> to the specific location <tt>i</tt>, throw
     * an MyNotSupportException when the location <tt>i</tt> is not feasible. if {@code i == 0}, insert as head and if
     * {@code i == this.size}, insert as tail.
     *
     * @param value the inserted element
     * @param i     the location
     * @throws MyNotSupportException
     */
    public void insert(E value, int i) throws MyNotSupportException {
        if (this.size < i)
            throw new MyNotSupportException("not support insert an element which go beyond the current elements");
        else {
            int start = 0;
            Node<E> temp = head, before = null;
            while (start < i) {
                before = temp;
                temp = temp.after;
                start++;
            }
            Node<E> newNode = new Node<E>(value, before, temp);
            if (before == null) this.head = newNode;
            else newNode.before.after = newNode;
            if (temp == null) this.tail = newNode;
            else newNode.after.before = newNode;
            this.size++;
        }
    }

    private boolean ensureSize(int i) {
        return this.size > i;
    }

    /**
     * return the element at location <tt>i</tt> , throw MyNosuchElementException when
     * the location i is not reliable. if {@code i == 0}, return the head and return the tail
     * if {@code i == this.size - 1}
     *
     * @param i the location
     * @return the element at location <tt>i</tt>
     * @throws MyNosuchElementException
     */
    public E get(int i) throws MyNosuchElementException {
        if (!ensureSize(i))
            throw new MyNosuchElementException("cannot find the element in specific location. i is too large");
        else {
            int start = 0;
            Node<E> temp = head;
            while (start < i) {
                temp = temp.after;
                start++;
            }
            return temp.value;
        }
    }

    /**
     * remove and return the element in specific location <tt>i</tt> , throw MyNosuchElementException
     * when the location <tt>i</tt> is not feasible. if {@code i == 0}, remove the head and remove the tail
     * if {@code i == this.size - 1}
     *
     * @param i the location
     * @return the removed element
     * @throws MyNosuchElementException
     */
    public E remove(int i) throws MyNosuchElementException {
        if (!this.ensureSize(i))
            throw new MyNosuchElementException("there is no such element in the specific location");
        else {
            Node<E> temp = head;
            int start = 0;
            while (start < i) {
                temp = temp.after;
                start++;
            }
            if (temp.before != null) temp.before.after = temp.after;
            else this.head = temp.after;
            if (temp.after != null) temp.after.before = temp.before;
            else this.tail = temp.before;
            this.size --;
            return temp.value;
        }
    }


    /**
     * clear the list.
     */
    public void clear() {
        this.size = 0;
        this.head = this.tail = null;
    }

    /**
     * return the current size of the list.
     *
     * @return the size of this list
     */
    public int getSize() {
        return this.size;
    }

}
