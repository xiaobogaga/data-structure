package io.github.xiaobogaga.hash;

/**
 * a simple hash table implementation by linear probing where h(i) = i.
 * the importance of hash table implementation by linear probing is the load factor and rehash,
 * the load factor for linear probing is set as less than 0.5.
 *
 * For linear probing strategy, the table size doesn't need to be a prime while for quadratic strategy,
 * a primed table size is necessary. see {@link HashTableByQuadraticProbing} for more detail.
 *
 * Another important issue is the lazy-deletion when removing a pair from this map.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @see HashTableByQuadraticProbing
 * @author tomzhu
 * @since 1.7
 */
public class HashTableByLinearProbing<K, V> {

    private int size = 0;
    private int capacity = 1 << 4;
    private Entry[] obs;
    private float loadFactor = 0.5f;

    /**
     * create a default hash table. with size 16 and load factor 0.5
     */
    public HashTableByLinearProbing() {
        this.obs = new HashTableByLinearProbing.Entry[this.capacity];
    }

    /**
     * construct a hash table by specifics some initial parameters
     *
     * @param initialCapacity
     * @param loadFactor
     */
    public HashTableByLinearProbing(int initialCapacity, float loadFactor) {
        this.capacity = initialCapacity <= 1 ? 1 : resize(initialCapacity);
        this.loadFactor = loadFactor;
        this.obs = new HashTableByLinearProbing.Entry[this.capacity];
    }

    private int resize(int initialCapacity) {
        initialCapacity |= initialCapacity >>> 1;
        initialCapacity |= initialCapacity >>> 2;
        initialCapacity |= initialCapacity >>> 4;
        initialCapacity |= initialCapacity >>> 8;
        initialCapacity |= initialCapacity >>> 16;
        return initialCapacity + 1;
    }

    /**
     * insert a k-v pair to this map. if a same key exists, the previous value would be replaced
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        int h = hash(key);
        int i = h & (this.capacity - 1);
        int start = 0;
        while (start < this.capacity) {
            if (this.obs[i] == null) {
                this.obs[i] = new Entry(key, value, h);
                this.size ++;
                break;
            } else if (this.obs[i].key.equals(key)) {
                this.obs[i].isDeleted = false;
                this.obs[i].value = value;
                return;
            }
            i = (h + start) & (this.capacity - 1);
            start ++;
        }
        // check whether the table needs resize.
        if (this.size >= this.capacity * this.loadFactor) {
            // needs to resize and rehash.
            // unfortunately, we need to travel all to rehash, actually, we can save these non empty element
            // location to a extra array. then it can save time when travel. here, we don't bother to do
            // this.
            this.capacity = this.capacity << 1;
            Entry[] newArr = new HashTableByLinearProbing.Entry[this.capacity];
            for (Entry entry : this.obs) {
                // this is really  time consuming.
                if (entry != null) {
                    int t = entry.hash & (this.capacity - 1);
                    int s = 1;
                    while (s < this.capacity) {
                        if (newArr[t] == null) {
                            newArr[t] = entry;
                            break;
                        }
                        t = (entry.hash + s) & (this.capacity - 1);
                        s ++;
                    }
                }
            }
            this.obs = newArr;
        }
    }

    /**
     * @param key
     * @return whether this map holds the key.
     */
    public boolean contains(K key) {
        int h = hash(key);
        int i = h & (this.capacity - 1);
        int start = 1;
        while (start < this.size) {
            if (this.obs[i] == null)
                return false;
            else if (this.obs[i].key.equals(key) && !this.obs[i].isDeleted)
                return true;
            else if (this.obs[i].key.equals(key))
                return false;
            i = (h + start) & (this.capacity - 1);
            start ++;
        }
        return false;
    }

    /**
     * attempt to remove a k pair from this map, return <tt>false</tt> if not found and return
     * <tt>true</tt> if success
     *
     * @param key
     * @return <tt>false</tt> if not found and return <tt>true</tt> if success
     */
    public boolean remove(K key) {
        int h = hash(key);
        int i = h & (this.capacity - 1);
        int start = 1;
        while (start < this.size) {
            if (this.obs[i] == null)
                return false;
            else if (this.obs[i].key.equals(key)) {
                this.obs[i].markDeleted();
                return true;
            }
            i = (h + start) & (this.capacity - 1);
            start ++;
        }
        return false;
    }

    private int hash(K key) {
        int h;
        return key == null ? 0 : ( (h = key.hashCode()) ^ (h >>> 16));
    }

    /**
     * try to get the associated value for key. return <tt>null</tt> if not found
     *
     * @param key
     * @return the associated value and return <tt>null</tt> if not found
     */
    public V get(K key) {
        int h = hash(key);
        int i = h & (this.capacity - 1);
        int start = 1;
        while (start < this.size) {
            if (this.obs[i] == null)
                return null;
            else if (this.obs[i].key.equals(key) && this.obs[i].isDeleted)
                return null;
            else if (this.obs[i].key.equals(key))
                return this.obs[i].value;
            i = (h + start) & (this.capacity - 1);
            start ++;
        }
        return null;
    }

    /**
     * a simple key value pair holder
     */
    class Entry {
        private K key;
        private V value;
        private int hash;
        private boolean isDeleted = false;

        public Entry(K key, V value, int hash, boolean isDeleted) {
            this.key = key;
            this.value = value;
            this.isDeleted = isDeleted;
            this.hash = hash;
        }

        public Entry(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        public void markDeleted() {
            this.isDeleted = true;
        }

    }

}
