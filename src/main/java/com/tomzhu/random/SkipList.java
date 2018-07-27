package com.tomzhu.random;

/**
 * a simple skip list implementation.

 * @author tomzhu.
 */
public class SkipList<E extends Comparable<E>> {

    private int MAX_LAVEL;
    private int level = -1;
    private Node header;
    private int size = 0;
    private float p = 0.5f;

    /**
     * node holder.
     */
    class Node {
        E value;
        Node[] forwards;
        public Node(E value, int level) {
            this.value = value;
            this.forwards = new SkipList.Node[level];
        }

    }

    /**
     * construct a skipList with max level.
     * @param MAX_LAVEL
     */
    public SkipList(int MAX_LAVEL) {
        this.MAX_LAVEL = MAX_LAVEL;
        this.header = new Node(null, this.MAX_LAVEL);
    }

    /**
     * @param ele insert an element to this skipList.
     */
    public void insert(E ele) {
        // set higher forwards.
        int k = randomLevel(), i = this.level;

        if (contains(ele))
            return;

        // System.out.println("insert " + k + " level, ele : " + ele);

        Node n = new Node(ele, k + 1);
        while (k > this.level) {
            this.level ++;
            this.header.forwards[this.level] = n;
        }

        // set lower forwards.
        Node h = this.header, p = null;
        int e = 0;
        i = k;
        while (i >= 0) {
            if ( (p = h.forwards[i]) != null) {
                if ( (e = p.value.compareTo(ele)) > 0) {
                    n.forwards[i] = p;
                    h.forwards[i] = n;
                    i--;
                } else if (e < 0){
                    h = p;
                } else {
                    i --;
                }
            } else {
                n.forwards[i] = p;
                h.forwards[i] = n;
                i --;
            }
        }
        this.size ++;
    }

    /**
     * generate random level with probability: Prob(level >= i) = p ^ (i -1 )
     * @return
     */
    private int randomLevel() {
        int level = 0;
        while ( Math.random() > p && level < MAX_LAVEL - 1) {
            level ++;
        }
        return level;
    }

    /**
     * @param ele
     * @return whether this skipList contains this element.
     */
    public boolean contains(E ele) {
        if (isEmpty())
            return false;
        Node h = this.header, p = null;
        int i = this.level, e = 0;
        while (i >= 0) {
            if ( (p = h.forwards[i]) != null) {
                if ((e = p.value.compareTo(ele)) == 0) {
                    return true;
                } else if (e > 0){
                    i--;
                } else {
                    h = p;
                }
            } else {
                i --;
            }

        }
        return false;
    }

    /**
     * remove an element from this skipList.
     * @param ele
     * @return
     */
    public boolean remove(E ele) {
        if (isEmpty())
            return false;
        Node h = this.header, p = null;
        int i = this.level, e = 0;
        boolean find = false;
        while (i >= 0) {
            if ( (p = h.forwards[i]) != null) {
                if ((e = p.value.compareTo(ele)) == 0) {
                    find = true;
                    h.forwards[i] = p.forwards[i];
                    i--;
                } else if (e > 0){
                    i--;
                } else {
                    h = p;
                }
            } else
                i--;
        }

        if (find)
            this.size --;
        return find;
    }

    /**
     * @return whether this skipList is empty.
     */
    public boolean isEmpty() {
        return  this.size == 0;
    }

}
