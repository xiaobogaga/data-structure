package io.github.xiaobogaga.hash;

/**
 * a simple quadratic probing hash table implementation where h(i) = i^2.
 * the load factor is less than 0.5, Unlike the linear probing, the table size for quadratic probing
 * must be a prime, so there is always a next prime procedure when the load factor is exceeded. Also,
 * here the lazy deletion is also used.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @see HashTableByLinearProbing
 * @author tomzhu
 * @since 1.7
 */
public class HashTableByQuadraticProbing<K, V> {

    private int size = 0;
    private float loadFactor = 0.5f;
    private int capacity = 17;
    private Entry[] obs;

    /**
     * a simple key value pair holder
     */
    class Entry {

        private K key;
        private V value;
        private int hash;
        private boolean isDeleted;

        public Entry(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.isDeleted = false;
        }

        public Entry(K key, V value, int hash, boolean isDeleted) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.isDeleted = isDeleted;
        }

        public void markDeleted() {
            this.isDeleted = true;
        }

    }

    /**
     * construct a default quadratic probing hash table with default settings.
     */
    public HashTableByQuadraticProbing() {
        this.obs = new HashTableByQuadraticProbing.Entry[this.capacity];
    }

    /**
     * insert a pair to this hashMap. if a same key exists, the previous value would be replaced
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null) {
                this.obs[i] = new Entry(key, value, h);
                this.size ++;
                break;
            } else if (this.obs[i].key.equals(key)) {
                this.obs[i].isDeleted = false;
                this.obs[i].value = value;
                return;
            }
            start ++;
            i = (t + start * start) % capacity;
        }

        // check whether needs to do rehash, if so, just perform rehash.
        if (this.size >= this.capacity * loadFactor) {
            this.capacity = nextPrime(this.capacity);
            Entry[] newArr = new HashTableByQuadraticProbing.Entry[this.capacity];
            for (Entry entry : this.obs) {
                if (entry != null) {
                    t = entry.hash % this.capacity;
                    i = t;
                    start = 1;
                    while (true) {
                        if (newArr[i] == null) {
                            newArr[i] = entry;
                            break;
                        }
                        start ++;
                        i = (t + start * start) % capacity;
                    }
                }
            }
            this.obs = newArr;
        }
    }

    /**
     * return the following prime.
     * @param lastPrime
     * @return
     */
    private int nextPrime(int lastPrime) {
        lastPrime += 2;
        while (true) {
            if (isPrime(lastPrime)) {
                return lastPrime;
            }
            lastPrime += 2;
        }
    }

    /**
     * verify whether the giving p is a prime.
     * @param p
     * @return
     */
    private boolean isPrime(int p) {
        int sqp = (int) Math.sqrt(p);
        for (int i = 3; i <= sqp; i += 2) {
            if (p % i == 0)
                return false;
        }
        return true;
    }

    /**
     * @param key
     * @return whether this map holds the element, return <tt>true</tt> if exist,
     * return <tt>false</tt> otherwise.
     */
    public boolean contains(K key) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null) {
                return false;
            } else if (this.obs[i].key.equals(key) && this.obs[i].isDeleted) {
                return false;
            } else if (this.obs[i].key.equals(key))
                return true;
            start ++;
            i = (t + start * start) % capacity;
        }
    }

    /**
     * try to remove a pair from this map, return <tt>true</tt> if success, return <tt>false</tt> if not found.
     *
     * @param key
     * @return <tt>true</tt> if success and <tt>false</tt> if such key not found
     */
    public boolean remove(K key) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null || (this.obs[i].key.equals(key) && this.obs[i].isDeleted) ) {
                return false;
            } else if (this.obs[i].key.equals(key)) {
                this.obs[i].markDeleted();
                return true;
            }
            start ++;
            i = (t + start * start) % capacity;
        }
    }

    /**
     * try to get and return the associated value for key, return <tt>null</tt> if no such key.
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null) {
                return null;
            } else if (this.obs[i].key.equals(key) && this.obs[i].isDeleted) {
                return null;
            } else if (this.obs[i].key.equals(key))
                return this.obs[i].value;
            start ++;
            i = (t + start * start) % capacity;
        }
    }

    private int hash(K key) {
        int h;
        h = key == null ? 0 : ((h = key.hashCode()) & (h >>> 16));
        return h < 0 ? -h : h;
    }

}
