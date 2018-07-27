package com.tomzhu.string;

/**
 * a basic trie implementation.
 * <p>
 * Generally, a tries is used for membership query. say given N strings M
 * and some query string t, we want to ask whether t_i exists in M. if the
 * size of t is 1, then this problem is very trivial, simply travels all
 * strings and perform match check.
 * <p>
 * However, things become complicated when there are many many querys, then
 * each membership query would consume much time. There is a solution by using
 * tries. Instead using array to save each string, we use an character tree to
 * represent all strings, basically, for each string, the character in strings
 * corresponds to a node in tries, for example, a string abac might like :
 * a
 * /
 * b
 * /
 * a
 * /
 * c
 * strings abac and abct looks like:
 * a
 * /
 * b
 * / \
 * a   c
 * /   /
 * c   t
 * so we can build a tries for the static string M. then for each membership query,
 * it only needs to visit the tries and find whether a match exist. then there is no
 * need to compare all strings each time. Actually, we can implement tries trivially
 * by a trees, each node has a character and its 256 children (for ASCII), then a giving
 * character match is O(1) and the time overhead of a simple query s would be 0(length(s))
 * <p>
 * Unfortunately, things become complicated again if M is very larger, then the space
 * overhead of tries built by above strategy is very huge. For example, a character needs
 * 1 byte plus 256 pointer, which is 1 byte + 256 * 4 byte about 1kB. The solution is
 * compression.
 * <p>
 * 1. we can use LinkedList instead of the pointer array with size 256.
 * 2. path compression strategy, for example: Patricia tree.
 */

public class BasicStringTrie {

    /**
     * Node holder.
     */
    class Node {

        /**
         * Node can hold a time variable for maintaining
         * times of a given string. However, for simplicity, I
         * don't use it here.
         */

        char c;
        Node[] nodes = new Node[256];

        public Node(char c) {
            this.c = c;
        }
    }

    private Node root;

    /**
     * construct a tries.
     */
    public BasicStringTrie() {
        this.root = new Node('\0');
    }

    /**
     * build a tries using string arrays ss.
     * @param ss
     * @return
     */
    public static BasicStringTrie buildTrie(String[] ss) {
        BasicStringTrie tries = new BasicStringTrie();
        for (String s : ss) {
            tries.insert(s);
        }
        return tries;
    }

    /**
     * @param s
     * @return whether this tries contains the string s.
     */
    public boolean contains(String s) {
        Node h = this.root;
        for (char c : s.toCharArray())
            if (h.nodes[c] != null) {
                h = h.nodes[c];
                continue;
            } else {
                return false;
            }
        return h.nodes['\0'] != null;
    }

    /**
     * insert a string s to this tries.
     * @param s
     */
    public void insert(String s) {
        Node h = this.root;
        for (char c : s.toCharArray())
            if (h.nodes[c] != null) {
                h = h.nodes[c];
                continue;
            } else {
                h.nodes[c] = new Node(c);
                h = h.nodes[c];
            }
        h.nodes['\0'] = new Node('\0');
    }

    /**
     * remove a string s from this tries.
     * @param s
     */
    public void remove(String s) {
        Node h = this.root;
        for (char c : s.toCharArray())
            if (h.nodes[c] != null) {
                h = h.nodes[c];
                continue;
            } else {
                return;
            }
        h.nodes['\0'] = null;
        return;
    }

}
