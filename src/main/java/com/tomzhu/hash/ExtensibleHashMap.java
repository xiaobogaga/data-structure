package com.tomzhu.hash;


import com.tomzhu.tree.KeydRedBlackTree;
import com.tomzhu.tree.Entry;
import java.util.Iterator;

/**
 * @author tomzhu.
 * we would like to compare extensible hashMap with standard java HashMap
 * and figure out the overhead of resize and rehash and its
 * impact to application performance.
 *
 * Like java HashMap, we will use a red-black tree as the seperate chain.
 */
public class ExtensibleHashMap<K extends Comparable<K>, V> {

    private int size = 0;
    private ExtensibleHashMap.Directory directory;
    private int bucketCapacity;

    /**
     * construct a extensible hash table with initial Depth and bucket capacity.
     *
     * @param initialDepth
     * @param bucketCapacity
     */
    public ExtensibleHashMap(int initialDepth, int bucketCapacity) {
        this.bucketCapacity = bucketCapacity <= 0 ? 4 : bucketCapacity;
        this.directory = new ExtensibleHashMap.Directory(initialDepth);
    }

    /**
     * construct a default setting extensible hash table with initial depth 2 and bucket capacity 4.
     */
    public ExtensibleHashMap() {
        this.bucketCapacity = 4;
        this.directory = new ExtensibleHashMap.Directory(2);
    }


    /**
     * simple Directory class implementation.
     */
    class Directory {

        private int globalDepth;
        private ExtensibleHashMap.Bucket[] buckets;
        private int bucketSize;

        /**
         * construct a directory with given initial depth.
         *
         * @param initialDepth
         */
        public Directory(int initialDepth) {
            this.globalDepth = initialDepth;
            this.bucketSize = 1 << this.globalDepth;
            this.buckets = new ExtensibleHashMap.Bucket[this.bucketSize];
            for (int i = 0; i < this.bucketSize; i++) {
                this.buckets[i] = new ExtensibleHashMap.Bucket(this.globalDepth);
            }
        }

        /**
         * construct a directory with default depth 2.
         */
        public Directory() {
            this.globalDepth = 2;
            this.bucketSize = 1 << 2;
            this.buckets = new ExtensibleHashMap.Bucket[this.bucketSize];
            for (int i = 0; i < this.bucketSize; i++) {
                this.buckets[i] = new ExtensibleHashMap.Bucket(this.globalDepth);
            }
        }

        /**
         * set directory depth
         *
         * @param depth
         */
        public void setGlobalDepth(int depth) {
            this.globalDepth = depth;
        }

        public int getGlobalDepth() {
            return this.globalDepth;
        }

        /**
         * grow the depth by 1.
         *
         * @param l
         */
        public void grow(int l) {
            this.globalDepth++;
            this.bucketSize = 1 << this.globalDepth;
            ExtensibleHashMap.Bucket[] newBuckets = new ExtensibleHashMap.Bucket[this.bucketSize];
            int t = 0;
            this.buckets[l].localDepth++;
            newBuckets[l] = new ExtensibleHashMap.Bucket(this.globalDepth);
            newBuckets[l + (1 << (this.globalDepth - 1))] = new ExtensibleHashMap.Bucket(this.globalDepth);
            for (ExtensibleHashMap.Bucket b : this.buckets) {
                if (t == l) {
                    Entry entry = null;
                    Iterator<Entry> ite = b.entries.iterator();
                    while (ite.hasNext()) {
                        entry = ite.next();
                        int l2 = entry.hash & (this.bucketSize - 1);
                        newBuckets[l2].insert(entry);
                    }
                } else {
                    newBuckets[t + (1 << (this.globalDepth - 1))] = newBuckets[t] = b;
                }
                t++;
            }
            this.buckets = newBuckets;
        }

        /**
         * try to split here since
         *
         * @param l
         */
        public void split(int l) {
            int l2 = 0, l3 = 0, i = 0;
            this.buckets[l].localDepth++;
            l2 = l + (1 << (this.buckets[l].localDepth - 1));
            this.buckets[l2] = new ExtensibleHashMap.Bucket(this.buckets[l].localDepth);
            Entry entry = null;
            int diff = 0;
            Iterator<Entry> iterator = this.buckets[l].entries.iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();
                l3 = entry.hash & ((1 << this.buckets[l].localDepth) - 1);
                if (l3 != l) {
                    diff = l3;
                    this.buckets[l3].insert(entry);
                    this.buckets[l].size--;
                }
                i++;
            }
            // removing entryies from previous.
            iterator = this.buckets[diff].entries.iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();
                this.buckets[l].entries.remove(entry);
            }

            // we need to set the corresponding bucket pointer
            broadCastChange(l2, this.buckets[l2].localDepth);
        }

        /**
         * broadcast change to higher nodes.
         * @param l
         * @param d
         */
        private void broadCastChange(int l, int d) {
            if ((l + (1 << d)) < this.bucketSize) {
                this.buckets[l + (1 << d)] = this.buckets[l];
                broadCastChange(l, d + 1);
                broadCastChange(l + (1 << d), d);
            }
        }
    }


    /**
     * bucket implementation holding entries.
     */
    class Bucket {

        private int localDepth;
        private KeydRedBlackTree<K, V> entries;
        private int size = 0;

        /**
         * construct a bucket with given depth.
         *
         * @param initialDepth
         */
        public Bucket(int initialDepth) {
            this.localDepth = initialDepth;
            this.entries = new KeydRedBlackTree();
        }

        /**
         * construct a bucket with depth 2.
         */
        public Bucket() {
            this.localDepth = 2;
            this.entries = new KeydRedBlackTree();
        }

        /**
         * set local depth.
         *
         * @param depth
         */
        public void setLocalDepth(int depth) {
            this.localDepth = depth;
        }

        public int getLocalDepth() {
            return this.localDepth;
        }

        public boolean insert(K key, V value, int hash) {
            return this.entries.insert(new Entry(key, value, hash));
        }

        public boolean insert(Entry entry) {
            return this.entries.insert(entry);
        }

    }

    /**
     * return the min same nodes under localDepth and globalDepth.
     * @param l
     * @param globalDepth
     * @param localDepth
     * @return
     */
    private int resolve(int l, int globalDepth, int localDepth) {
        int h;
        while (globalDepth > localDepth) {
            if ((h = l - (1 << (globalDepth - 1))) >= 0)
                l = h;
            globalDepth--;
        }
        return l;
    }

    /**
     * insert a key-value pair
     *
     * @param key
     * @param value
     */
    public boolean insert(K key, V value) {
        int h = hash(key);
        int l = h & (this.directory.bucketSize - 1);
        int l2 = resolve(l, this.directory.globalDepth, this.directory.buckets[l].localDepth);
        if (this.directory.buckets[l].size >= this.bucketCapacity) {
            // needs split.
            while (this.directory.buckets[l2].localDepth
                    < this.directory.globalDepth) {
                this.directory.split(l2);
                if (this.directory.buckets[l].size < this.bucketCapacity) {
                    if (this.directory.buckets[l].insert(key, value, h))
                        this.size++;
                    return true;
                }
                l2 = h & ((1 << this.directory.buckets[l2].localDepth) - 1);
            }
            do {
                // needs to grow.
                this.directory.grow(l);
                l = hash(key) & (this.directory.bucketSize - 1);
            } while (this.directory.buckets[l].size >= this.bucketCapacity);
            if (this.directory.buckets[l].insert(key, value, h))
                this.size ++;
        } else {
            // just insert.
            if (this.directory.buckets[l].insert(key, value, h))
                this.size ++;
        }
        return true;
    }

    /**
     * check whether this map contains the key.
     *
     * @param key
     * @return
     */
    public boolean contains(K key) {
        int h = hash(key);
        int l = hash(key) & (this.directory.bucketSize - 1);
        ExtensibleHashMap.Bucket b = this.directory.buckets[l];
        return b.entries.containsKey(key);
    }

    /**
     * replace the given key with new value.
     *
     * @param key
     * @param value
     */
    private void replace(K key, V value) {
        if (!this.contains(key))
            return;
        int h = hash(key);
        int l = h & (this.directory.bucketSize - 1);
        ExtensibleHashMap.Bucket b = this.directory.buckets[l];
        b.entries.insert(new Entry(key, value, h));
    }


    /**
     * try to remove an key from this map, return false if not found.
     *
     * @param key
     * @return
     */
    public boolean remove(K key) {
        int h = hash(key);
        int l = h & (this.directory.bucketSize - 1);
        ExtensibleHashMap.Bucket b = this.directory.buckets[l];
        boolean ret = b.entries.removeByKey(key);
        if (ret)
            this.size --;
        return ret;
    }

    /**
     * return the hash of given key.
     *
     * @param key
     * @return
     */
    private int hash(K key) {
        int h;
        h = key == null ? 0 : ((h = key.hashCode()) ^ (h >>> 16));
        return h < 0 ? -h : h;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }


}
