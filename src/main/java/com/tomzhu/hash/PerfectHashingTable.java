package com.tomzhu.hash;

import java.util.Random;

/**
 * a simple perfect hashing table implementation.
 * perfect hashing table is a location table without collision. Perfect Hashing is used in this scenario where
 * the all key-value pair are known in advance.
 *
 * for PerfectHashingTable, a general use procedure is that: first adding all key-value pairs to
 * this hash table, then call {@code build} method to create a collision-free hash table. after built,
 * the insert method cannot be called.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @author tomzhu
 * @since 1.7
 */
public class PerfectHashingTable<K, V> {

    private int capacity;
    private int size = 0;
    private int prime = (1 << 31) - 1;
    private Entry[] obs;
    private Object[] chains;
    private HashFunction hashFunction;
    private boolean isSecondary;
    private Random randA;
    private Random randB;

    /**
     * construct a perfect location table by the element size N
     * @param N
     */
    public PerfectHashingTable(int N) {
        this.capacity = N;
        this.obs = new PerfectHashingTable.Entry[N];
        randA = new Random(this.capacity);
        randB = new Random(this.capacity + 1); // this is for primary hash.
    }

    /**
     * construct a secondary hash table, only used by inner procedure.
     * @param N
     * @param isSecondary
     */
    private PerfectHashingTable(int indicator, int N, boolean isSecondary) {
        this.capacity = N * N;
        this.isSecondary = isSecondary;
        this.obs = new PerfectHashingTable.Entry[N];
        this.randA = new Random(indicator + this.capacity);
        this.randB = new Random(indicator + this.capacity + 1);
    }


    /**
     * insert the key-value pair
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        this.obs[this.size++] = new Entry(key, value);
    }

    /**
     * insert all key-value pair.
     * @param total
     */
    public void insertAll(Entry[] total) {
        this.obs = total;
    }

    /**
     * insert an key-value pair entry.
     * @param entry
     */
    public void insert(Entry entry) {
        this.obs[this.size++] = entry;
    }

    /**
     * will try many random location function to build a collision-free
     * location table, after build, the insert procedure are not allowed any more.
     */
    public void build() {
        int[] secondaryHashTableSize = new int[this.capacity];
        while (true) {
            // try location functions until the primary location table generate appropriate
            // secondary location table.
            HashFunction f = hashFamily(this.capacity);
            for (Entry entry : this.obs) {
                entry.location = f.hash(entry.key);
                secondaryHashTableSize[entry.location] ++;
            }
            if (isAppropriateSecondaryHashTable(secondaryHashTableSize)) {
                // try to distribute these pairs to secondary location table.
                this.hashFunction = f;
                this.chains = new Object[this.capacity];
                for (Entry entry : this.obs) {
                    if (this.chains[entry.location] == null) {
                        this.chains[entry.location] = new PerfectHashingTable<K, V>(entry.location,
                                secondaryHashTableSize[entry.location], true);
                    }
                    ((PerfectHashingTable)this.chains[entry.location]).insert(entry);
                    if ( ((PerfectHashingTable) this.chains[entry.location]).size ==
                            secondaryHashTableSize[entry.location]) {
                        // all items are added, start build.
                        ((PerfectHashingTable) this.chains[entry.location]).buildSecondaryPerfectHashTable();
                    }
                }
                return; // finish build.
            } else {
                secondaryHashTableSize = new int[this.capacity];
                continue;
            }
        }
    }

    /**
     * start build the secondary hash table.
     */
    private void buildSecondaryPerfectHashTable() {
        this.chains = new Object[this.capacity];
        boolean keep = false;
        int i = 0, size = this.capacity;
        do {
            HashFunction f = hashFamily(this.capacity);
            this.hashFunction = f;
            keep = false;
            for (Entry entry : this.obs) {
                entry.location = f.hash(entry.key);
                if (this.chains[entry.location] != null) {
                    i = 0;
                    while (i < this.capacity) {
                        this.chains[i++] = null;
                    }
                    keep = true;
                    break;
                } else {
                    this.chains[entry.location] = entry;
                }
            }
        } while (keep);
    }


    private boolean isAppropriateSecondaryHashTable(int[] tables) {
        int total = 0;
        for (int i : tables) {
            total += i * i;
        }
        return total <= 4 * this.capacity;
    }

    public class Entry {
        private K key;
        private V value;
        private int location;
        private HashFunction hashFunction;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public Entry(K key, V value, HashFunction hashFunction) {
            this.key = key;
            this.value = value;
        }

        public void setLocation(int location) {
            this.location = location;
        }
    }

    /**
     * a simple class represents Hash Functions
     */
    class HashFunction {
        private int a;
        private int b;
        private int prime;
        private int tableSize;
        public HashFunction(int a, int b, int prime, int tableSize) {
            this.a = a;
            this.b = b;
            this.prime = prime;
            this.tableSize = tableSize;
        }
        public int hash(K key) {
            int hashcode = key.hashCode();
            int h = hashcode * a + b;
            if (h < 0)
                h = -h;
            return (h % prime) % this.tableSize;
        }

    }

    /**
     * @param tableSize
     * @return a hash Function.
     */
    public HashFunction hashFamily(int tableSize) {
        int a = randA.nextInt(prime - 1);
        int b = randB.nextInt(prime - 1);
        return new HashFunction(a, b, prime, tableSize);
    }

    /**
     * @param key
     * @return whether this map holds this key element
     */
    public boolean contains(K key) {
        int l = hashFunction.hash(key);
        if (this.chains[l] != null) {
            PerfectHashingTable<K, V> secondaryChain = ((PerfectHashingTable)this.chains[l]);
            int l2 = secondaryChain.hashFunction.hash(key);
            return secondaryChain.chains[l2] != null &&
                    ((Entry) secondaryChain.chains[l2]).key.equals(key);
        } else {
            return false;
        }
    }

    /**
     * try to remove a key-value pair from this map, return <tt>false</tt> if not found.
     *
     * @param key
     * @return <tt>true</tt> if success and <tt>false</tt> if such key not found
     */
    public boolean remove(K key) {
        int l = hashFunction.hash(key);
        if (this.chains[l] != null) {
            PerfectHashingTable<K, V> secondaryChain = ((PerfectHashingTable)this.chains[l]);
            int l2 = secondaryChain.hashFunction.hash(key);
            if (((Entry) secondaryChain.chains[l2]).key.equals(key)) {
                secondaryChain.chains[l2] = null;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * try to get the associated value for the key. and return <tt>null</tt> if no such
     * key found
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int l = hashFunction.hash(key);
        if (this.chains[l] != null) {
            PerfectHashingTable<K, V> secondaryChain = ((PerfectHashingTable)this.chains[l]);
            int l2 = secondaryChain.hashFunction.hash(key);
            return (secondaryChain.chains[l2] != null &&
                    ((Entry) secondaryChain.chains[l2]).key.equals(key)) ?
                    ((Entry) secondaryChain.chains[l2]).value: null;
        } else {
            return null;
        }
    }

}
