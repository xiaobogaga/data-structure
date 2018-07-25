package com.tomzhu.hash;

/**
 * a simple extensible hash table implementation.
 */
public class ExtensibleHashTable<K, V> {

    private int size = 0;
    private Directory directory;
    private int bucketCapacity;

    /**
     * construct a extensible hash table with initial Depth and bucket capacity.
     *
     * @param initialDepth
     * @param bucketCapacity
     */
    public ExtensibleHashTable(int initialDepth, int bucketCapacity) {
        this.bucketCapacity = bucketCapacity <= 0 ? 4 : bucketCapacity;
        this.directory = new Directory(initialDepth);
    }

    /**
     * construct a default setting extensible hash table with initial depth 2 and bucket capacity 4.
     */
    public ExtensibleHashTable() {
        this.bucketCapacity = 4;
        this.directory = new Directory(2);
    }


    /**
     * simple Directory class implementation.
     */
    class Directory {

        private int globalDepth;
        private Bucket[] buckets;
        private int bucketSize;

        /**
         * construct a directory with given initial depth.
         *
         * @param initialDepth
         */
        public Directory(int initialDepth) {
            this.globalDepth = initialDepth;
            this.bucketSize = 1 << this.globalDepth;
            this.buckets = new ExtensibleHashTable.Bucket[this.bucketSize];
            for (int i = 0; i < this.bucketSize; i++) {
                this.buckets[i] = new Bucket(this.globalDepth);
            }
        }

        /**
         * construct a directory with default depth 2.
         */
        public Directory() {
            this.globalDepth = 2;
            this.bucketSize = 1 << 2;
            this.buckets = new ExtensibleHashTable.Bucket[this.bucketSize];
            for (int i = 0; i < this.bucketSize; i++) {
                this.buckets[i] = new Bucket(this.globalDepth);
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
            Bucket[] newBuckets = new ExtensibleHashTable.Bucket[this.bucketSize];
            int t = 0;
            this.buckets[l].localDepth++;
            newBuckets[l] = new Bucket(this.globalDepth);
            newBuckets[l + (1 << (this.globalDepth - 1))] = new Bucket(this.globalDepth);
            for (Bucket b : this.buckets) {
                if (t == l) {
                    for (Entry entry : b.entries) {
                        int l2 = hash(entry.key) & (this.bucketSize - 1);
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
            this.buckets[l2] = new Bucket(this.buckets[l].localDepth);
            for (Entry entry : this.buckets[l].entries) {
                l3 = hash(entry.key) & ((1 << this.buckets[l].localDepth) - 1);
                if (l3 != l) {
                    this.buckets[l3].insert(entry);
                    this.buckets[l].entries[i] = null;
                    this.buckets[l].size--;
                }
                i++;
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
        private Entry[] entries;
        private int size = 0;

        /**
         * construct a bucket with given depth.
         *
         * @param initialDepth
         */
        public Bucket(int initialDepth) {
            this.localDepth = initialDepth;
            this.entries = new ExtensibleHashTable.Entry[bucketCapacity];
        }

        /**
         * construct a bucket with depth 2.
         */
        public Bucket() {
            this.localDepth = 2;
            this.entries = new ExtensibleHashTable.Entry[bucketCapacity];
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

        public void insert(K key, V value) {
            for (int i = 0; i < bucketCapacity; i++) {
                if (this.entries[i] == null) {
                    // insert here.
                    this.entries[i] = new Entry(key, value);
                    this.size++;
                    return;
                }
            }
            return;
        }

        public void insert(Entry entry) {
            for (int i = 0; i < bucketCapacity; i++) {
                if (this.entries[i] == null) {
                    // insert here.
                    this.entries[i] = entry;
                    this.size++;
                    return;
                }
            }
            return;
        }

    }

    /**
     * key-value pair holder.
     */
    class Entry {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
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
        if (contains(key)) {
            replace(key, value);
            return true;
        }
        int h = hash(key);
        int l = h & (this.directory.bucketSize - 1);
        int l2 = resolve(l, this.directory.globalDepth, this.directory.buckets[l].localDepth);
        if (this.directory.buckets[l].size >= this.bucketCapacity) {
            // needs split.
            while (this.directory.buckets[l2].localDepth
                    < this.directory.globalDepth) {
                this.directory.split(l2);
                if (this.directory.buckets[l].size < this.bucketCapacity) {
                    this.directory.buckets[l].insert(key, value);
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
            this.directory.buckets[l].insert(key, value);
        } else {
            // just insert.
            this.directory.buckets[l].insert(key, value);
        }
        this.size++;
        return true;
    }

    /**
     * check whether this map contains the key.
     *
     * @param key
     * @return
     */
    public boolean contains(K key) {
        int l = hash(key) & (this.directory.bucketSize - 1);
        Bucket b = this.directory.buckets[l];
        for (Entry e : b.entries) {
            if (e != null && e.key.equals(key))
                return true;
        }
        return false;
    }

    /**
     * replace the given key with new value.
     *
     * @param key
     * @param value
     */
    private void replace(K key, V value) {
        int l = hash(key) & (this.directory.bucketSize - 1);
        Bucket b = this.directory.buckets[l];
        for (Entry e : b.entries) {
            if (e != null && e.key.equals(key))
                e.value = value;
        }
    }


    /**
     * try to remove an key from this map, return false if not found.
     *
     * @param key
     * @return
     */
    public boolean remove(K key) {
        int l = hash(key) & (this.directory.bucketSize - 1);
        Bucket b = this.directory.buckets[l];
        for (int i = 0; i < bucketCapacity; i++) {
            if (b.entries[i] != null && b.entries[i].key.equals(key)) {
                b.entries[i] = null;
                b.size--;
                this.size--;
                return true;
            }
        }
        return false;
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


}
