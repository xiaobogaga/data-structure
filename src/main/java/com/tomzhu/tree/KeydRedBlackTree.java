package com.tomzhu.tree;

/**
 * a keyd red black tree implementation. since a red black tree only can holding keys, but instead red black
 * tree can hold key value pairs. and {@link HashMap} based on this implementation.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author tomzhu
 * @since 1.7
 */
public class KeydRedBlackTree<K extends Comparable<K>, V> extends RedBlackTree {

    /**
     * @param key
     * @return whether this tree contains the key. return <tt>true</tt> if found, return <tt>false</tt> otherwise.
     */
    public boolean containsKey(K key) {
        Node r = this.root;
        int z;
        while (r != null) {
            if ((z = ((Entry) r.ele).key.compareTo(key)) == 0)
                return true;
            else if (z < 0) {
                r = r.right;
            } else {
                r = r.left;
            }
        }
        return false;
    }

    /**
     * try to remove a key-value pair. return true if success and false otherwise.
     *
     * @param key
     * @return true if success and false if not found.
     */
    public boolean removeByKey(K key) {
        if (isEmpty())
            return false;
        Node r = this.root;
        int z = 0;
        while (r != null) {
            if ( (z = ((Entry) r.ele).key.compareTo(key)) == 0) {
                break;
            } else if (z > 0) {
                r = r.left;
            } else
                r = r.right;
        }
        if (r != null) {
            Node c = successorNode(r);
            if (c == null) {
                // node r has no children.
                // just remove this node and will be ok.
                removeNode(r);
                if (r.color == Color.BLACK) {
                    // todo, need adjust
                    adjustForRemove(null, r.parent);
                }
            } else if (c.left == null && c.right == null) {
                // the replacing node r has no children, replace and do adjusting.
                r.ele = c.ele;
                if (c.parent.left == c)
                    c.parent.left = null;
                else
                    c.parent.right = null;
                if (c.color == Color.BLACK) {
                    // todo, need adjust
                    adjustForRemove(null, c.parent);
                }
            } else {
                // since c has only one child, replace r with c,
                // then color r.child as black.
                Node t = replaceNode(r, c);
                t.color = Color.BLACK;
            }
            this.size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * insert a key value pair. and if a same key already exist, just replacing the value with new value.
     *
     * @param key
     * @param value
     * @param hash
     * @return <tt>true</tt> if success and <tt>false</tt> if found duplicate items.
     */
    public boolean insert(K key, V value, int hash) {
        return insert(new Entry<K, V>(key, value, hash));
    }

    /**
     * @param key
     * @return the value associated with the key key.
     */
    public V get(K key) {
        Node r = this.root;
        int z;
        while (r != null) {
            if ((z = ((Entry) r.ele).key.compareTo(key)) == 0)
                return (V) ((Entry) r.ele).value;
            else if (z < 0) {
                r = r.right;
            } else {
                r = r.left;
            }
        }
        return null;
    }

}