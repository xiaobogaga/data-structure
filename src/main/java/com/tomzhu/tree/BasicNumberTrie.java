package com.tomzhu.tree;

/**
 * a simple tries implementation for numbers 0-9.
 *
 * for string, see {@link BasicStringTrie}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicNumberTrie {

    /**
     * Node holder.
     */
    class Node {

        /**
         * Node can hold a time variable for maintaining
         * times of a given string. However, for simplicity, I
         * don't use it here.
         */

        int number;
        Node[] nodes = new Node[11];
        public Node(int number) {
            this.number = number;
        }
    }

    private Node root;

    /**
     * construct a simple number tries.
     */
    public BasicNumberTrie() {
        this.root = new Node(-1);
    }

    /**
     * construct a number tries using the given numbers.
     *
     * @param numbers a string array where each item is an char array of number[0-9].
     */
    public static BasicNumberTrie buildBasicNumberTries(String[] numbers) {
        BasicNumberTrie numberTrie = new BasicNumberTrie();
        for (String s : numbers) {
            numberTrie.insert(s);
        }
        return numberTrie;
    }

    /**
     * @param numbers
     * @return whether this tries contains given number string.
     */
    public boolean contains(String numbers) {
        Node r = this.root;
        for (char c : numbers.toCharArray()) {
            if (r.nodes[c - '0'] != null) {
                r = r.nodes[c - '0'];
            } else {
                return false;
            }
        }
        return r.nodes[10] != null;
    }

    /**
     * remove a numbers
     *
     * @param numbers
     */
    public void remove(String numbers) {
        Node r = this.root;
        for (char c : numbers.toCharArray()) {
            if (r.nodes[c - '0'] != null) {
                r = r.nodes[c - '0'];
            } else {
                return;
            }
        }
        r.nodes[10] = null;
        return;
    }

    /**
     * insert a numbers.
     *
     * @param numbers
     */
    public void insert(String numbers) {
        Node r = this.root;
        for (char c : numbers.toCharArray()) {
            if (r.nodes[c - '0'] != null) {
                r = r.nodes[c - '0'];
            } else {
                r.nodes[c - '0'] = new Node((int) (c - '0'));
                r = r.nodes[c - '0'];
            }
        }
        r.nodes[10] = new Node(0);
        return;
    }

}
