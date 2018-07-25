package com.tomzhu.tree;

/**
 * a simple hashMap implementation like {@link java.util.HashMap} based on red-black tree.
 */
public class HashMap<K extends Comparable<K>, V> {

    private int size = 0;
    private int capacity = 0;
    private float loadFactor = 0.75f;
    private int tableSize;

    private  RedBlackTree<K> tree;

    public HashMap() {
        this.capacity = 16;
    }

    public HashMap(int capacity) {
        this.capacity = capacity <= 0 ? 16 : sizeFor(capacity);
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


    public void remove(K key) {
        // todo
        return;
    }

    public void put(K key, V value) {
        // todo
        return;
    }

    public boolean contains(K key) {
        // todo.
        return false;
    }


}
