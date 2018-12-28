package com.tomzhu.tree;

import com.tomzhu.string.BitMap;

/**
 * a simple hashMap implementation like {@link java.util.HashMap} based on {@link KeydRedBlackTree}
 */
public class HashMap<K extends Comparable<K>, V> {

    private int size = 0;
    private int capacity = 0;
    private float loadFactor = 0.75f;

    private KeydRedBlackTree<K, V>[] holders;

    public HashMap() {
        this.capacity = 16;
        initSaver();
    }

    public HashMap(int capacity) {
        this.capacity = capacity <= 0 ? 16 : sizeFor(capacity);
        initSaver();
    }

    public HashMap(int capacity, float loadFactor) {
        this.capacity = capacity <= 0 ? 16 : sizeFor(capacity);
        this.loadFactor = loadFactor;
        initSaver();
    }

    private void initSaver() {
        this.holders = new KeydRedBlackTree[capacity];
    }

    private int sizeFor(int capacity) {
        capacity |= capacity >>> 1;
        capacity |= capacity >>> 2;
        capacity |= capacity >>> 4;
        capacity |= capacity >>> 8;
        capacity |= capacity >>> 16;
        return capacity + 1;
    }

    private int hash(K key) {
        int h = key == null ? 0 : key.hashCode();
        if (h < 0)
            h = -h;
        return h ^ (h >>> 16);
    }

    public boolean remove(K key) {
        int hashCode = hash(key);
        int loc = hashCode & (this.capacity - 1);
        boolean ans = (this.holders[loc] != null && this.holders[loc].removeByKey(key));
        if (ans) this.size --;
        return ans;
    }

    public boolean put(K key, V value) {
        int hashCode = hash(key);
        int loc = hashCode & (this.capacity - 1);
        // System.out.println("inserting " + key + " with hashCode : " + hashCode);
        boolean ans = false;
        if (this.holders[loc] == null) this.holders[loc] = new KeydRedBlackTree<K, V>();
        if ( (ans = this.holders[loc].insert(key, value, hashCode)) ) {
            this.size ++;
        }
        // check whether needs rehashing
        if (this.size > this.loadFactor * this.capacity) {
            rehash();
        }
        return ans;
    }

    public V get(K key) {
        int hashCode = hash(key);
        int loc = hashCode & (this.capacity - 1);
        if (this.holders[loc] == null) return null;
        return this.holders[loc].get(key);
    }

    public boolean contains(K key) {
        int hashCode = hash(key);
        int loc = hashCode & (this.capacity - 1);
        return this.holders[loc] != null && this.holders[loc].containsKey(key);
    }

    public int getSize() {
        return this.size;
    }

    public float getLoadFactor() {
        return this.loadFactor;
    }

    public void setLoadFactor(float loadFactor) {
        this.loadFactor = loadFactor;
    }

    private void rehash() {
        int oldCap = this.capacity;
        this.capacity = this.capacity << 1;
        KeydRedBlackTree<K, V>[] arr = new KeydRedBlackTree[this.capacity];
        System.arraycopy(this.holders, 0, arr, 0, oldCap);
        this.holders = arr;
        for (int i = 0; i < oldCap; i++) if (this.holders[i] != null) doRehash(i, oldCap);
    }

    private void doRehash(int loc, int oldCap) {
        this.holders[loc].root = null;
        this.holders[loc].size = 0;
        int newLoc = oldCap + loc;
        RedBlackTree.Node node = this.holders[loc].head;
        RedBlackTree.Node head1 = null, tail1 = null;
        RedBlackTree.Node head2 = null, tail2 = null;
        RedBlackTree.Node temp;
        while (node != null) {
            temp = node;
            temp.left = temp.right = temp.parent = null;
            node = node.next;
            int indicator = ((Entry) temp.ele).hash & (this.capacity - 1);
            if (indicator != loc) {
                if (head2 == null) {
                    head2 = tail2 = temp;
                    head2.prev = head2.next = null;
                } else {
                    temp.prev = tail2;
                    tail2.next = temp;
                    tail2 = temp;
                    tail2.next = null;
                }
            } else {
                if (head1 == null) {
                    head1 = tail1 = temp;
                    head1.prev = head1.next = null;
                } else {
                    temp.prev = tail1;
                    tail1.next = temp;
                    tail1 = temp;
                    tail1.next = null;
                }
            }
        }
        this.holders[loc].head = head1;
        this.holders[loc].tail = tail1;
        if (this.holders[newLoc] == null) this.holders[newLoc] = new KeydRedBlackTree<K, V>();
        this.holders[newLoc].head = head2;
        this.holders[newLoc].tail = tail2;
        RedBlackTree.Node node1 = head1, node2 = head2;
        while (node1 != null) {
            temp = node1;
            node1 = node1.next;
            this.holders[loc].insertForRehash(temp);
        }
        while (node2 != null) {
            temp = node2;
            node2 = node2.next;
            this.holders[newLoc].insertForRehash(temp);
        }
    }

}
