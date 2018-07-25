package com.tomzhu.tree;

/**
 * Created by tomzhu on 18-4-5.
 * a simple map implementation based on binary search tree.
 */
public class TreeMap<E extends Comparable<E>, V> {

    // java.util.TreeMap

    class Entry implements Comparable<Entry> {
        E key;
        V value;

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj instanceof TreeMap.Entry) {
                Entry entry = (Entry) obj;
                return entry.key.compareTo(this.key) == 0;
            } else {
                return false;
            }
        }

        public E getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(E key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry(E key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(Entry o) {
            return this.key.compareTo(o.key);
        }
    }

    private BinarySearchTree<Entry> tree;

    public TreeMap() {
        this.tree = new BinarySearchTree<Entry>();
    }

    /**
     * check whether this tree contains the element.
     * @param key
     * @return
     */
    public boolean contains(E key) {
        // this is really ugly
        return this.tree.contains(new Entry(key, null));
    }

    public void insert(E key, V value) {
        this.tree.insert(new Entry(key , value));
    }

    /**
     * remove a item specified by key from this map.
     * @param key
     * @return
     */
    public boolean remove(E key) {
        if (this.contains(key)) {
            this.tree.remove(new Entry(key, null));
            return true;
        } else {
            return false;
        }
    }


}
