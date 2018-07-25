package com.tomzhu.tree;

/**
 * key-value pair holder.
 */
public class Entry<K extends Comparable, V> implements Comparable<Entry> {
    public K key;
    public V value;
    public int hash;

    public Entry(K key, V value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }

    public int compareTo(Entry obj) {
        return this.key.compareTo(obj == null ? null : obj.key);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Entry) {
            return ((Entry) o).key.equals(this.key);
        } else {
            return false;
        }
    }

}