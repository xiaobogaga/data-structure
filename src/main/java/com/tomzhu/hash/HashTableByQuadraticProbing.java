package com.tomzhu.hash;

/**
 * a simple quadratic probing hash tbale implementation where h(i) = i^2.
 * the load factor is less than 0.5, Unlike the linear probing, the table size for quadratic probing
 * must be a prime, so there is always a next prime procedure when the load factor is exceeded.
 */
public class HashTableByQuadraticProbing<K, V> {

    private int size = 0;
    private float loadFactor = 0.5f;
    private int capacity = 17;
    private Entry[] obs;

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
     * insert a pair to this hashMap.
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
     * check whether this map holds the element.
     * return true if exist, return false otherwise.
     * @param key
     * @return
     */
    public boolean contains(K key) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null) {
                return false;
            } else if (this.obs[i].key.equals(key) && !this.obs[i].isDeleted) {
                return true;
            } else if (this.obs[i].key.equals(key))
                return false;
            start ++;
            i = (t + start * start) % capacity;
        }
    }

    /**
     * try to remove a pair from this map, return true if success, return false if
     * not found.
     * @param key
     * @return
     */
    public boolean remove(K key) {
        int h = hash(key);
        int t = h % this.capacity;
        int i = t;
        int start = 1;
        while (true) {
            if (this.obs[i] == null) {
                return false;
            } else if (this.obs[i].key.equals(key)) {
                this.obs[i].markDeleted();
                return true;
            }
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
