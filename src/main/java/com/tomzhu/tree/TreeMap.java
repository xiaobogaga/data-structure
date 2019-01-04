package com.tomzhu.tree;

/**
 * a simple map implementation based on binary search tree. Actually, in
 * {@link java.util.TreeMap}, it is based on red-black tree, here we just use binary search tree.
 * generally, this is more time-consumed but changing to red-black tree is very simple. another issue
 * is that java treeMap provides an ordered travel based on comparator. but this treeMap doesn't consider
 * this for simplify. Actually, it is really easy implemented, we can own them by a in order travel
 * on binary search tree.
 *
 * @param <E> the key type
 * @param <V> the value type
 *
 * @author tomzhu
 * @since 1.7
 */
public class TreeMap<E extends Comparable<E>, V> {

    /**
     * simple entry class.
     */
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

    /**
     * construct a treeMap.
     */
    public TreeMap() {
        this.tree = new BinarySearchTree<Entry>();
    }

    /**
     * @param key
     * @return whether this tree contains the element.
     */
    public boolean contains(E key) {
        // this is really ugly
        return this.tree.contains(new Entry(key, null));
    }

    /**
     * insert a key, value to this treeMap.
     * @param key
     * @param value
     * @return <tt>true</tt> if success and <tt>false</tt> if just replace the previous value
     */
    public boolean insert(E key, V value) {
        return this.tree.insert(new Entry(key , value));
    }

    /**
     * remove a item specified by key from this map.
     *
     * @param key
     * @return true if found, false otherwise.
     */
    public boolean remove(E key) {
        if (this.contains(key)) {
            this.tree.remove(new Entry(key, null));
            return true;
        } else {
            return false;
        }
    }

    /**
     * try to get the associated value of key. return <tt>null</tt> if not found.
     *
     * @param key
     * @return the founded value and <tt>null</tt> if not found.
     */
    public V get(E key) {
        Entry ans = this.tree.get(new Entry(key, null));
        return ans == null ? null : ans.value;
    }

    /**
     * @return whether this TreeMap is empty.
     */
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }


}
