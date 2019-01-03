package com.tomzhu.hash;

import java.util.Random;

/**
 * a simple cuckoo hashing table implementation. if the data sets are known in advance, then
 * cuckoo hashing could be a perfect hashing table.
 * <p>
 * this implementation allows multiple hash table and correspondingly multiple hash functions. but either
 * 2 or 4 are allowed, since more hash table has less contributions to the load factor.
 * for 2 hash table, the load factor is 0.4;
 * for 4 hash table, the load factor is 0.8;
 *
 * so this hash table created by a initial capacity and a hash type which specifics how many hash table this cuckoo
 * hashing would use.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @author tomzhu
 * @since 1.7
 */
public class CuckooHashTable<K, V> {

    /**
     * a cuckoo hash table type holder.
     */
    public enum HashTableType {
        TWO, FOUR
    }

    private HashTableType hashTableType = HashTableType.TWO;
    private int capacity = 17;
    private int size = 0;
    private float loadFactor = 0.4f;
    private Object[][] tables;
    private HashFamily hashFamily;
    private HashFunction[] hashFunctions;
    private int k = 2;
    private int allowableSteps;
    private int allowableRehashes = 16;

    /**
     * construct a cuckoo hash table with 2 hash function and initial capacity 17.
     */
    public CuckooHashTable() {
        this.tables = new Object[2][this.capacity];
        this.hashFamily = new HashFamily(2);
        this.hashFunctions = this.hashFamily.generateHashFunctions();
        this.allowableSteps = this.k * 4;
    }

    /**
     * construct a cuckoo hash table by given parameters.
     *
     * @param initialCapacity
     * @param hashTableType
     */
    public CuckooHashTable(int initialCapacity, HashTableType hashTableType) {
        this.capacity = nextPrime(initialCapacity);
        this.hashTableType = hashTableType;
        if (this.hashTableType == HashTableType.FOUR) {
            this.loadFactor = 0.8f;
            this.tables = new Object[4][this.capacity];
            this.hashFamily = new HashFamily(4);
            this.hashFunctions = this.hashFamily.generateHashFunctions();
            this.k = 4;
        } else {
            this.tables = new Object[2][this.capacity];
            this.hashFamily = new HashFamily(2);
            this.hashFunctions = this.hashFamily.generateHashFunctions();
        }
        this.allowableSteps = this.k * 4;
    }

    /**
     * @param bound
     * @return a following primer for bound.
     */
    private int nextPrime(int bound) {
        bound = (bound & 1) == 0 ? bound + 1 : bound;
        for (; ; bound += 2) {
            if (isPrime(bound))
                return bound;
        }
    }

    /**
     * @param tester
     * @return whether the given number is a primer.
     */
    private boolean isPrime(int tester) {
        int p = (int) Math.sqrt(tester);
        for (int i = 3; i <= p; i += 2) {
            if (tester % i == 0)
                return false;
        }
        return true;
    }

    /**
     * try to insert a key, value pair to t his hash table. if a same key exists, the previous value would be replaced
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        // do replacing
        for (int i = 0; i < this.k; i++) {
            int h = this.hashFunctions[i].hash(key);
            Object o = this.tables[i][h % this.capacity];
            if (o != null && ((Entry) o).key.equals(key)) {
                ((Entry) o).value = value;
                return;
            }
        }
        // add
        Entry o = new Entry(key, value);
        Entry temp;
        int pos = 0;
        int steps = 0;
        int rehashTimes = 0;
        int l = 0;
        while (true) {
            steps = 0;
            while (steps < allowableSteps) {
                l = this.hashFunctions[pos].hash(o.key) % this.capacity;
                temp = (Entry) this.tables[pos][l];
                if (temp == null) {
                    this.tables[pos][l] = o;
                    this.size++;
                    if (this.size >= this.capacity * this.loadFactor) {
                        expand();
                    }
                    return;
                } else {
                    this.tables[pos][l] = o;
                    o = temp;
                    pos = (pos + 1) & (this.k - 1);
                    steps++;
                }
            }

            if (rehashTimes >= allowableRehashes) {
                expand();
            }
            rehash();
        }
    }

    /**
     * expand capacity
     */
    private void expand() {
        this.capacity = nextPrime( (int) (this.capacity / this.loadFactor) );
        boolean success = false;
        while (!success) {
            Object[][] newTables = new Object[this.k][this.capacity];
            success = true;
            for (Object[] tb : this.tables) {
                for (Object o : tb) {
                    if (o != null && !this.insertForRehash(newTables, (Entry) o)) {
                        success = false;
                        this.hashFunctions = this.hashFamily.generateHashFunctions();
                        break;
                    }
                }
                if (!success)
                    break;
            }
            if (success) {
                this.tables = newTables;
            }
        }
    }

    /**
     * perform rehash.
     */
    private void rehash() {
        boolean success = false;
        while (!success) {
            this.hashFunctions = this.hashFamily.generateHashFunctions();
            Object[][] newTables = new Object[this.k][this.capacity];
            success = true;
            for (Object[] tb : this.tables) {
                for (Object o : tb) {
                    if (o != null && !this.insertForRehash(newTables, (Entry) o)) {
                        success = false;
                        break;
                    }
                }
                if (!success)
                    break;
                else
                    this.tables = newTables;
            }
        }
    }

    /**
     * used by rehash procedure.
     * @param entry
     * @return
     */
    private boolean insertForRehash(Object[][] tables, Entry entry) {
        Entry o = entry;
        Entry temp;
        int pos = 0;
        int steps = 0;
        int l = 0;
        while (steps < allowableSteps) {
            l = this.hashFunctions[pos].hash(o.key) % this.capacity;
            temp = (Entry) tables[pos][l];
            if (temp == null) {
                tables[pos][l] = o;
                return true;
            } else {
                tables[pos][l] = o;
                o = temp;
                pos = (pos + 1) & (this.k - 1);
                steps++;
            }
        }
        return false;
    }

    /**
     * @param key
     * @return whether this table contains the key.
     */
    public boolean contains(K key) {
        for (int i = 0; i < this.k; i++) {
            int h = this.hashFunctions[i].hash(key);
            Object o = this.tables[i][h % this.capacity];
            if (o != null && ((Entry) o).key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * try to remove a key-value pair from table, return <tt>false</tt> if not found.
     *
     * @param key
     * @return
     */
    public boolean remove(K key) {
        for (int i = 0; i < this.k; i++) {
            Object o = this.tables[i][this.hashFunctions[i].hash(key) % this.capacity];
            if (o != null && ((Entry) o).key.equals(key)) {
                this.tables[i][this.hashFunctions[i].hash(key) % this.capacity] = null;
                this.size--;
                return true;
            }
        }
        return false;
    }

    /**
     * try to get the associated value for the key. return <tt>null</tt> if not found.
     *
     * @param key
     * @return the associated value and <tt>null</tt> if no such key exists
     */
    public V get(K key) {
        for (int i = 0; i < this.k; i++) {
            int h = this.hashFunctions[i].hash(key);
            Object o = this.tables[i][h % this.capacity];
            if (o != null && ((Entry) o).key.equals(key)) {
                return ((Entry) o).value;
            }
        }
        return null;
    }

    /**
     * a simple hash function generator, can generate k independent hash functions.
     */
    class HashFamily {

        private int k;
        private Random rand = new Random(System.currentTimeMillis());

        public HashFamily(int k) {
            this.k = k;
        }

        public HashFunction[] generateHashFunctions() {
            HashFunction[] functions = new CuckooHashTable.HashFunction[this.k];
            for (int i = 0; i < k; i++) {
                functions[i] = makeNewRandomFunction(i);
            }
            return functions;
        }

        public HashFunction makeNewRandomFunction(int indicator) {
            int a = rand.nextInt();
            int b = rand.nextInt();
            a = a < 0 ? -a : a;
            b = b < 0 ? -b : b;
            return new HashFunction(a, b);
        }


    }

    /**
     * a hash function whose scheme like h(i) = i * multipler + adder;
     */
    class HashFunction {

        private int adder;
        private int multipler;

        public HashFunction(int adder, int multipler) {
            this.adder = adder;
            this.multipler = multipler;
        }

        public int hash(K key) {
            int h = key.hashCode() * this.multipler + this.adder;
            return h < 0 ? -h : h;
        }

    }

    /**
     * a simple class for holding key and value Pair.
     */
    class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

}
